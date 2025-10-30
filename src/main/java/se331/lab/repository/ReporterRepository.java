package se331.lab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.Reporter;
import se331.lab.security.user.User;

public interface ReporterRepository extends JpaRepository<Reporter, Long> {
    Optional<Reporter> findByUser(User user);
}
