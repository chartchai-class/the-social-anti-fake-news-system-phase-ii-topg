package se331.lab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.News;

public interface NewsDao {
    Page<News> getNews(Pageable pageRequest);
    News getNews(Long id);
    News save(News news);
    Integer getNewsSize();
}
