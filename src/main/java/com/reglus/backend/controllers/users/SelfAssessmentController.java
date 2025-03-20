package com.reglus.backend.controllers.users;
import com.reglus.backend.model.entities.users.smf.SelfAssessment;
import com.reglus.backend.model.entities.users.smf.SelfAssessmentRequest;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.users.SelfAssessmentRepository;
import com.reglus.backend.repositories.users.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/self-assessments")
@CrossOrigin(origins = "*")
public class SelfAssessmentController {

    @Autowired
    private SelfAssessmentRepository selfAssessmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<List<SelfAssessment>> getAllSelfAssessments() {
        try {
            List<SelfAssessment> selfAssessments = selfAssessmentRepository.findAll();

            if (selfAssessments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(selfAssessments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SelfAssessment> getSelfAssessmentById(@PathVariable("id") Long id) {
        Optional<SelfAssessment> selfAssessmentData = selfAssessmentRepository.findById(id);
        return selfAssessmentData.map(selfAssessment -> new ResponseEntity<>(selfAssessment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SelfAssessment> updateSelfAssessment(@PathVariable("id") Long id, @RequestBody SelfAssessmentRequest selfAssessmentRequest) {
        Optional<SelfAssessment> selfAssessmentData = selfAssessmentRepository.findById(id);

        if (selfAssessmentData.isPresent()) {
            SelfAssessment existingSelfAssessment = selfAssessmentData.get();

            if (selfAssessmentRequest.getPerformanceEvaluation() != null) {
                existingSelfAssessment.setPerformanceEvaluation(selfAssessmentRequest.getPerformanceEvaluation());
            }
            if (selfAssessmentRequest.getStrengths() != null) {
                existingSelfAssessment.setStrengths(selfAssessmentRequest.getStrengths());
            }
            if (selfAssessmentRequest.getImprovementAreas() != null) {
                existingSelfAssessment.setImprovementAreas(selfAssessmentRequest.getImprovementAreas());
            }

            SelfAssessment updatedSelfAssessment = selfAssessmentRepository.save(existingSelfAssessment);
            return new ResponseEntity<>(updatedSelfAssessment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
