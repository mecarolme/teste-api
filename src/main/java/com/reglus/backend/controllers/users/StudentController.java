package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.entities.users.StudentRequest;
import com.reglus.backend.model.entities.users.smf.*;
import com.reglus.backend.repositories.users.SocialAspectRepository;
import com.reglus.backend.model.enums.UserType;
import com.reglus.backend.repositories.users.StudentRepository;
import com.reglus.backend.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SocialAspectRepository socialAspectRepository;

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest studentRequest) {
        try {
            User user = new User();
            user.setUserType(UserType.STUDENT);
            user.setEmail(studentRequest.getEmail());
            user.setPasswordHash(studentRequest.getPasswordHash());
            user.setName(studentRequest.getName());
            user.setDateBirth(studentRequest.getDateBirth());
            user.setEducationLevel(studentRequest.getEducationLevel());
            user.setInstituteName(studentRequest.getInstituteName());
            userRepository.save(user);

            Student student = new Student();
            student.setUser(user);
            student.setState(studentRequest.getState());
            student.setCity(studentRequest.getCity());

            studentRepository.save(student);

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody StudentRequest studentRequest) {
        try {
            Optional<Student> studentData = studentRepository.findById(id);

            if (studentData.isPresent()) {
                Student student = studentData.get();

                Optional<User> optionalUser = userRepository.findById(student.getUser().getUserId());
                if (!optionalUser.isPresent()) {
                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }

                User user = optionalUser.get();

                user.setEmail(studentRequest.getEmail());
                user.setPasswordHash(studentRequest.getPasswordHash());
                user.setName(studentRequest.getName());
                user.setDateBirth(studentRequest.getDateBirth());
                user.setEducationLevel(studentRequest.getEducationLevel());
                user.setInstituteName(studentRequest.getInstituteName());
                userRepository.save(user);

                student.setUser(user);
                student.setState(studentRequest.getState());
                student.setCity(studentRequest.getCity());
                studentRepository.save(student);

                return new ResponseEntity<>(student, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Optional<Student> studentData = studentRepository.findById(id);

        if (studentData.isPresent()) {
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable("email") String email) {
        Optional<Student> studentData = studentRepository.findByUserEmail(email);

        if (studentData.isPresent()) {
            return ResponseEntity.ok(studentData.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") Long id) {
        try {
            Optional<Student> studentData = studentRepository.findById(id);

            if (studentData.isPresent()) {
                Student student = studentData.get();
                User user = student.getUser();

                studentRepository.deleteById(id);
                userRepository.delete(user);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
