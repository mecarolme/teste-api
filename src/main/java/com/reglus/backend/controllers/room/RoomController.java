package com.reglus.backend.controllers.room;

import com.reglus.backend.model.entities.rooms.Course;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.rooms.RoomRequest;
import com.reglus.backend.model.entities.rooms.RoomStudent;
import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.repositories.room.CourseRepository;
import com.reglus.backend.repositories.room.RoomRepository;
import com.reglus.backend.repositories.room.RoomStudentRepository;
import com.reglus.backend.repositories.users.EducatorRepository;
import com.reglus.backend.repositories.users.StudentRepository;
import com.reglus.backend.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EducatorRepository educatorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoomStudentRepository roomStudentRepository;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomRequest roomRequest) {
        Educator educator = educatorRepository.findById(roomRequest.getEducatorId())
                .orElseThrow(() -> new RuntimeException("Educator not found"));

        Course course = new Course();
        course.setName(roomRequest.getCourseName());
        course.setDescription(roomRequest.getCourseDescription());
        course.setPrice(roomRequest.getCoursePrice());
        course.setPeriod(roomRequest.getCoursePeriod());
        course.setSchedule(roomRequest.getCourseSchedule());
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        course = courseRepository.save(course);

        Room room = new Room();
        room.setName(roomRequest.getName());
        room.setStartDate(roomRequest.getStartDate());

        if (roomRequest.getEndDate() != null) {
            room.setEndDate(roomRequest.getEndDate());
        }

        room.setCourse(course);
        room.setEducator(educator);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());
        room = roomRepository.save(room);

        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms(@RequestParam Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Room> allRooms = roomRepository.findAll();

        List<Room> availableRooms = new ArrayList<>();
        for (Room room : allRooms) {
            boolean isAlreadyEnrolled = roomStudentRepository.findByRoom_RoomId(room.getRoomId()).stream()
                    .anyMatch(rs -> rs.getStudent().getStudentId().equals(studentId));

            if (!isAlreadyEnrolled) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    @PostMapping("/enroll")
    public String enrollInRoom(@RequestParam Long studentId, @RequestParam Long roomId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        boolean isAlreadyEnrolled = roomStudentRepository.findByRoom_RoomId(roomId).stream()
                .anyMatch(rs -> rs.getStudent().getStudentId().equals(studentId));

        if (isAlreadyEnrolled) {
            return "Student is already enrolled in this room";
        }

        RoomStudent roomStudent = new RoomStudent();
        roomStudent.setRoom(room);
        roomStudent.setStudent(student);
        roomStudent.setEnrollmentDate(LocalDate.from(LocalDateTime.now()));
        roomStudentRepository.save(roomStudent);

        return "Student enrolled successfully in the room";
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
        return roomRepository.findById(roomId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{roomId}/students")
    public ResponseEntity<List<Student>> getStudentsByRoom(@PathVariable Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        List<RoomStudent> roomStudents = roomStudentRepository.findByRoom_RoomId(roomId);
        List<Student> students = roomStudents.stream()
                .map(RoomStudent::getStudent)
                .collect(Collectors.toList());

        return ResponseEntity.ok(students);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Room>> getRoomsForStudent(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<RoomStudent> roomStudents = roomStudentRepository.findByStudent_StudentId(studentId);

        List<Room> rooms = roomStudents.stream()
                .map(RoomStudent::getRoom)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/educator/{educatorId}")
    public ResponseEntity<List<Room>> getRoomsByEducatorId(@PathVariable Long educatorId) {
        Educator educator = educatorRepository.findById(educatorId)
                .orElseThrow(() -> new RuntimeException("Educator not found"));

        List<Room> rooms = roomRepository.findByEducator(educator);

        return ResponseEntity.ok(rooms);
    }
}
