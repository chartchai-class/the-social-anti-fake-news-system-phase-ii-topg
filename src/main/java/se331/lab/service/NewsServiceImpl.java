package se331.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.lab.dao.NewsDao;
import se331.lab.entity.News;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    final NewsDao newsDao;

    @Override
    public Integer getNewsSize() {
        return newsDao.getNewsSize();
    }

    @Override
    public Page<News> getNews(Integer pageSize, Integer page) {
        return newsDao.getNews(PageRequest.of(page - 1, pageSize));
    }

    @Override
    public News getNews(Long id) {
        return newsDao.getNews(id);
    }

    @Override
    public News save(News news) {
        return newsDao.save(news);
    }

    @Override
    public Page<News> getNews(String query, Pageable pageable) {
        // Search by topic, shortDetail, reporter
        return newsDao.getNewsByTopicOrShortDetailOrReporter(query, query, query, pageable);
    }

    @Override
    public Page<News> getNews(Integer pageSize, Integer page, String status) {
        return newsDao.getNews(pageSize, page, status);
    }

    // NEW: With isAdmin parameter for status filtering
    @Override
    public Page<News> getNews(Integer pageSize, Integer page, String status, boolean isAdmin) {
        return newsDao.getNews(pageSize, page, status, isAdmin);
    }

    // NEW: With isAdmin parameter for search
    @Override
    public Page<News> getNews(String query, Pageable pageable, boolean isAdmin) {
        return newsDao.getNewsByTopicOrShortDetailOrReporter(query, query, query, pageable, isAdmin);
    }
}