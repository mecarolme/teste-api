package com.reglus.backend.repositories.users;
import com.reglus.backend.model.entities.users.smf.InterestHobby;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface InterestHobbyRepository extends JpaRepository<InterestHobby, Long> {
}
