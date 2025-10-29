package se331.lab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import se331.lab.dao.ReporterDao;
import se331.lab.entity.Reporter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporterServiceImpl implements ReporterService {

    private final ReporterDao reporterDao;

    @Override
    public List<Reporter> getAllReporter() {
        return reporterDao.getReporter(Pageable.unpaged()).getContent();
    }

    @Override
    public Page<Reporter> getReporter(Integer page, Integer pageSize) {
        return reporterDao.getReporter(PageRequest.of(page, pageSize));
    }
}
