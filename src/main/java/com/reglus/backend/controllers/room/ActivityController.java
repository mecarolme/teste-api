package com.reglus.backend.controllers.room;

import com.reglus.backend.model.entities.rooms.Activity;
import com.reglus.backend.model.entities.rooms.ActivityRequest;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.repositories.room.ActivityRepository;
import com.reglus.backend.repositories.room.RoomRepository;
import com.reglus.backend.repositories.users.EducatorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {

    private final ActivityRepository activityRepository;
    private final RoomRepository roomRepository;
    private final EducatorRepository educatorRepository;

    public ActivityController(ActivityRepository activityRepository, RoomRepository roomRepository, EducatorRepository educatorRepository) {
        this.activityRepository = activityRepository;
        this.roomRepository = roomRepository;
        this.educatorRepository = educatorRepository;
    }

    @PostMapping
    public ResponseEntity<?> createActivity(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("roomId") Long roomId,
            @RequestParam("educatorId") Long educatorId,
            @RequestParam("maxPoints") Integer maxPoints,
            @RequestParam("dataLimit") LocalDateTime dataLimit,
            @RequestParam(value = "fileData", required = false) MultipartFile fileData) {
        try {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found with ID: " + roomId));

            Educator educator = educatorRepository.findById(educatorId)
                    .orElseThrow(() -> new RuntimeException("Educator not found with ID: " + educatorId));

            Activity activity = new Activity();
            activity.setRoom(room);
            activity.setEducator(educator);
            activity.setTitle(title);
            activity.setDescription(description);
            activity.setMaxPoints(maxPoints);
            activity.setDataLimit(dataLimit);
            activity.setCreatedAt(LocalDateTime.now());
            activity.setUpdatedAt(LocalDateTime.now());

            // Salvar o arquivo se existir
            if (fileData != null && !fileData.isEmpty()) {
                activity.setFileData(fileData.getBytes());
            }

            Activity savedActivity = activityRepository.save(activity);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/room/{roomId}/activities")
    public ResponseEntity<?> getActivitiesByRoomId(@PathVariable Long roomId) {
        try {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found with ID: " + roomId));

            List<Activity> activities = activityRepository.findByRoom(room);

            return ResponseEntity.ok(activities);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<?> getActivityById(@PathVariable Long activityId) {
        try {
            Activity activity = activityRepository.findById(activityId)
                    .orElseThrow(() -> new RuntimeException("Atividade não encontrada com ID: " + activityId));

            return ResponseEntity.ok(activity);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
