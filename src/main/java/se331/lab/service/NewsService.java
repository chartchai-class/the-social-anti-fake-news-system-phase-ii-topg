package se331.lab.service;

import org.springframework.data.domain.Page;
import se331.lab.entity.News;

public interface NewsService {
    Integer getNewsSize();
    Page<News> getNews(Integer pageSize, Integer page);
    News getNews(Long id);
    News save(News news);
}
