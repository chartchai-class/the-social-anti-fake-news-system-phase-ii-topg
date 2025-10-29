package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.Reporter;

public interface ReporterRepository extends JpaRepository<Reporter, Long> {

}
