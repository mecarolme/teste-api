package com.reglus.backend.repositories.room;

import com.reglus.backend.model.entities.rooms.Activity;
import com.reglus.backend.model.entities.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByRoom(Room room);
}