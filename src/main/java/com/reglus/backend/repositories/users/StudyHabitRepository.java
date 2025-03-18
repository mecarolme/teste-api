package com.reglus.backend.repositories.users;

import com.reglus.backend.model.entities.users.smf.StudyHabit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyHabitRepository extends JpaRepository<StudyHabit, Long> {
}
