package com.reglus.backend.controllers.users;
import com.reglus.backend.model.entities.users.smf.InterestHobby;
import com.reglus.backend.model.entities.users.smf.InterestHobbyRequest;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.users.InterestHobbyRepository;
import com.reglus.backend.repositories.users.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/interest-hobbies")
@CrossOrigin(origins = "*")
public class InterestHobbyController {

    @Autowired
    private InterestHobbyRepository interestHobbyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<List<InterestHobby>> getAllInterestHobbies() {
        try {
            List<InterestHobby> interestHobbies = interestHobbyRepository.findAll();

            if (interestHobbies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(interestHobbies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterestHobby> getInterestHobbyById(@PathVariable("id") Long id) {
        Optional<InterestHobby> interestHobbyData = interestHobbyRepository.findById(id);
        return interestHobbyData.map(interestHobby -> new ResponseEntity<>(interestHobby, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterestHobby> updateInterestHobby(@PathVariable("id") Long id, @RequestBody InterestHobbyRequest interestHobbyRequest) {
        Optional<InterestHobby> interestHobbyData = interestHobbyRepository.findById(id);

        if (interestHobbyData.isPresent()) {
            InterestHobby existingInterestHobby = interestHobbyData.get();

            if (interestHobbyRequest.getActivitiesOutsideSchool() != null) {
                existingInterestHobby.setActivitiesOutsideSchool(interestHobbyRequest.getActivitiesOutsideSchool());
            }
            if (interestHobbyRequest.getDreamsGoals() != null) {
                existingInterestHobby.setDreamsGoals(interestHobbyRequest.getDreamsGoals());
            }

            InterestHobby updatedInterestHobby = interestHobbyRepository.save(existingInterestHobby);

            return new ResponseEntity<>(updatedInterestHobby, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteInterestHobby(@PathVariable("id") Long id) {
        try {
            Optional<InterestHobby> interestHobbyData = interestHobbyRepository.findById(id);

            if (interestHobbyData.isPresent()) {
                InterestHobby interestHobby = interestHobbyData.get();
                Student student = interestHobby.getStudent();

                if (student != null) {
                    student.setInterestHobby(null); // Assuming a setInterestHobby method exists
                    studentRepository.save(student);
                }

                interestHobbyRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
