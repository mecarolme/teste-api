package com.reglus.backend.repositories.room;

import com.reglus.backend.model.entities.rooms.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}