package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findByTopicIgnoreCaseContainingOrShortDetailIgnoreCaseContainingOrReporterIgnoreCaseContaining(
            String topic, String shortDetail, String reporter, Pageable pageable
    );
}
