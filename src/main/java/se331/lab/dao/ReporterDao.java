package se331.lab.dao;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.Reporter;

public interface ReporterDao {
    Page<Reporter> getReporter(Pageable pageRequest);
    Optional<Reporter> findById(Long id);
}
