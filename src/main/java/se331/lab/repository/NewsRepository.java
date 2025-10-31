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
    // For admins - shows all news including hidden
    @Query("""
           SELECT n FROM News n
           WHERE (:status = 'all')
              OR (:status = 'True' AND n.trueVotes > n.falseVotes)
              OR (:status = 'False' AND n.falseVotes > n.trueVotes)
              OR (:status = 'Pending' AND n.trueVotes = n.falseVotes)
           """)
    Page<News> findByStatus(@Param("status") String status, Pageable pageable);

    // For non-admins - excludes hidden news (treats null as false)
    @Query("""
           SELECT n FROM News n
           WHERE (n.hidden IS NULL OR n.hidden = false)
             AND ((:status = 'all')
              OR (:status = 'True' AND n.trueVotes > n.falseVotes)
              OR (:status = 'False' AND n.falseVotes > n.trueVotes)
              OR (:status = 'Pending' AND n.trueVotes = n.falseVotes))
           """)
    Page<News> findByStatusExcludingHidden(@Param("status") String status, Pageable pageable);

    // For admins - search with all news
    Page<News> findByTopicIgnoreCaseContainingOrShortDetailIgnoreCaseContainingOrReporter_NameIgnoreCaseContaining(
            String topic, String shortDetail, String reporterName, Pageable pageable
    );

    // For non-admins - search excluding hidden news (treats null as false)
    @Query("""
           SELECT n FROM News n
           WHERE (n.hidden IS NULL OR n.hidden = false)
             AND (LOWER(n.topic) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(n.shortDetail) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(n.reporter.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
           """)
    Page<News> findByKeywordExcludingHidden(@Param("keyword") String keyword, Pageable pageable);
}