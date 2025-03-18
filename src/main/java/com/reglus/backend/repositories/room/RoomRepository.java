package com.reglus.backend.repositories.room;

import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.users.Educator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByEducator(Educator educator);
}