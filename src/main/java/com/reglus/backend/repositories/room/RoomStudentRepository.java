package com.reglus.backend.repositories.room;

import com.reglus.backend.model.entities.rooms.RoomStudent;
import com.reglus.backend.model.entities.users.Educator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomStudentRepository extends JpaRepository<RoomStudent, Long> {
    List<RoomStudent> findByRoom_RoomId(Long roomId);
    List<RoomStudent> findByStudent_StudentId(Long studentId);
}