package com.reglus.backend.repositories.users;
import com.reglus.backend.model.entities.users.smf.HealthWellbeing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthWellbeingRepository extends JpaRepository<HealthWellbeing, Long>  {
}
