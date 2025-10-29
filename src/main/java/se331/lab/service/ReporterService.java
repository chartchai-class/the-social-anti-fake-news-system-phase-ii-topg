package se331.lab.service;

import org.springframework.data.domain.Page;
import java.util.List;
import se331.lab.entity.Reporter;

public interface ReporterService {
    List<Reporter> getAllReporter();
    Page<Reporter> getReporter(Integer page, Integer pageSize);
}
