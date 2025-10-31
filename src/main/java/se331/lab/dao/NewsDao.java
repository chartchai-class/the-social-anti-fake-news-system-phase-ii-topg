package se331.lab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.News;

public interface NewsDao {
    Page<News> getNews(Pageable pageRequest);
    News getNews(Long id);
    News save(News news);
    Integer getNewsSize();

    // Search by topic, shortDetail, or reporter
    Page<News> getNewsByTopicOrShortDetailOrReporter(String topic, String shortDetail, String reporter, Pageable pageable);

    // NEW: Search with isAdmin flag
    Page<News> getNewsByTopicOrShortDetailOrReporter(String topic, String shortDetail, String reporter, Pageable pageable, boolean isAdmin);

    Page<News> getNews(Integer pageSize, Integer page, String status);

    // NEW: Get news with isAdmin flag
    Page<News> getNews(Integer pageSize, Integer page, String status, boolean isAdmin);
}