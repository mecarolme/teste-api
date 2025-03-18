package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.smf.SocialAspect;
import com.reglus.backend.model.entities.users.smf.SocialAspectRequest;
import com.reglus.backend.repositories.users.SocialAspectRepository;
import com.reglus.backend.repositories.users.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/social-aspects")
@CrossOrigin(origins = "*")
public class SocialAspectController {
    @Autowired
    private SocialAspectRepository socialAspectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<List<SocialAspect>> getAllSocialAspects() {
        try {
            List<SocialAspect> socialAspects = socialAspectRepository.findAll();

            if (socialAspects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(socialAspects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialAspect> getSocialAspectById(@PathVariable("id") Long id) {
        Optional<SocialAspect> socialAspectData = socialAspectRepository.findById(id);
        return socialAspectData.map(socialAspect -> new ResponseEntity<>(socialAspect, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialAspect> updateSocialAspect(@PathVariable("id") Long id, @RequestBody SocialAspectRequest socialAspectRequest) {
        Optional<SocialAspect> socialAspectData = socialAspectRepository.findById(id);

        if (socialAspectData.isPresent()) {
            SocialAspect existingSocialAspect = socialAspectData.get();

            if (socialAspectRequest.getLivingWith() != null) {
                existingSocialAspect.setLivingWith(socialAspectRequest.getLivingWith());
            }
            if (socialAspectRequest.getRelationshipWithClassmates() != null) {
                existingSocialAspect.setRelationshipWithClassmates(socialAspectRequest.getRelationshipWithClassmates());
            }
            if (socialAspectRequest.getRelationshipWithTeachers() != null) {
                existingSocialAspect.setRelationshipWithTeachers(socialAspectRequest.getRelationshipWithTeachers());
            }
            if (socialAspectRequest.getRelationshipWithFamily() != null) {
                existingSocialAspect.setRelationshipWithFamily(socialAspectRequest.getRelationshipWithFamily());
            }

            SocialAspect updatedSocialAspect = socialAspectRepository.save(existingSocialAspect);
            return new ResponseEntity<>(updatedSocialAspect, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
