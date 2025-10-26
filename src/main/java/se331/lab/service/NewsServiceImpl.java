package se331.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import se331.lab.entity.News;
import se331.lab.repository.NewsRepository;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    final NewsRepository newsRepository;

    @Override
    public Page<News> getNews(Integer perPage, Integer page) {
        return newsRepository.findAll(PageRequest.of(page - 1, perPage));
    }

    @Override
    public News getNews(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }
}
