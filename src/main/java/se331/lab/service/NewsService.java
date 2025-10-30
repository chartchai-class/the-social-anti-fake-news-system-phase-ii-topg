package se331.lab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.News;

public interface NewsService {
    Integer getNewsSize();
    Page<News> getNews(Integer pageSize, Integer page);
    Page<News> getNews(String query, Pageable pageable);
    Page<News> getNews(Integer pageSize, Integer page, String status);
    News getNews(Long id);
    News save(News news);
}
