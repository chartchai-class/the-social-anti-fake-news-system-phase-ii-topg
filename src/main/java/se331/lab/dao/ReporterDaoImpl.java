package se331.lab.dao;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.lab.entity.Reporter;
import se331.lab.repository.ReporterRepository;

@Repository
@RequiredArgsConstructor
public class ReporterDaoImpl implements ReporterDao {

    private final ReporterRepository reporterRepository;

    @Override
    public Page<Reporter> getReporter(Pageable pageRequest) {
        return reporterRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Reporter> findById(Long id){
        return reporterRepository.findById(id);
    }
}
