package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.smf.HealthWellbeing;
import com.reglus.backend.model.entities.users.smf.HealthWellbeingRequest;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.users.HealthWellbeingRepository;
import com.reglus.backend.repositories.users.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/healthwellbeing")
@CrossOrigin(origins = "*")
public class HealthWellbeingController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HealthWellbeingRepository healthWellbeingRepository;

    @GetMapping
    public ResponseEntity<List<HealthWellbeing>> getAllHealthWellbeing() {
        try {
            List<HealthWellbeing> healthWellbeings = healthWellbeingRepository.findAll();
            if (healthWellbeings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(healthWellbeings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthWellbeing> getHealthWellbeingById(@PathVariable("id") Long id) {
        Optional<HealthWellbeing> healthWellbeingData = healthWellbeingRepository.findById(id);
        return healthWellbeingData.map(healthWellbeing -> new ResponseEntity<>(healthWellbeing, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthWellbeing> updateHealthWellbeing(
            @PathVariable("id") Long id,
            @RequestBody HealthWellbeingRequest healthWellbeingRequest) {

        Optional<HealthWellbeing> healthWellbeingData = healthWellbeingRepository.findById(id);

        if (healthWellbeingData.isPresent()) {
            HealthWellbeing existingHealthWellbeing = healthWellbeingData.get();

            if (healthWellbeingRequest.getHealthCondition() != null) {
                existingHealthWellbeing.setHealthCondition(healthWellbeingRequest.getHealthCondition());
            }
            if (healthWellbeingRequest.getPhysicalActivity() != null) {
                existingHealthWellbeing.setPhysicalActivity(healthWellbeingRequest.getPhysicalActivity());
            }
            if (healthWellbeingRequest.getDietaryEvaluation() != null) {
                existingHealthWellbeing.setDietaryEvaluation(healthWellbeingRequest.getDietaryEvaluation());
            }
            if (healthWellbeingRequest.getSleepHours() != null) {
                existingHealthWellbeing.setSleepHours(healthWellbeingRequest.getSleepHours());
            }

            HealthWellbeing updatedHealthWellbeing = healthWellbeingRepository.save(existingHealthWellbeing);
            return new ResponseEntity<>(updatedHealthWellbeing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
