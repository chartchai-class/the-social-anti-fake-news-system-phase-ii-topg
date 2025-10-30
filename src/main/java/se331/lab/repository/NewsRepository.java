package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("""
           SELECT n FROM News n
           WHERE (:status = 'all')
              OR (:status = 'True' AND n.trueVotes > n.falseVotes)
              OR (:status = 'False' AND n.falseVotes > n.trueVotes)
              OR (:status = 'Pending' AND n.trueVotes = n.falseVotes)
           """)
    Page<News> findByStatus(@Param("status") String status, Pageable pageable);

    Page<News> findByTopicIgnoreCaseContainingOrShortDetailIgnoreCaseContainingOrReporter_NameIgnoreCaseContaining(
            String topic, String shortDetail, String reporterName, Pageable pageable
    );
}
