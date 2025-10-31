package se331.lab.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.lab.entity.News;
import se331.lab.repository.NewsRepository;

@Repository
@RequiredArgsConstructor
public class NewsDaoImpl implements NewsDao {
    final NewsRepository newsRepository;

    @Override
    public Page<News> getNews(Pageable pageRequest) {
        return newsRepository.findAll(pageRequest);
    }

    @Override
    public News getNews(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public Integer getNewsSize() {
        return Math.toIntExact(newsRepository.count());
    }

    @Override
    public Page<News> getNewsByTopicOrShortDetailOrReporter(String topic, String shortDetail, String reporter, Pageable pageable) {
        return newsRepository.findByTopicIgnoreCaseContainingOrShortDetailIgnoreCaseContainingOrReporter_NameIgnoreCaseContaining(
                topic, shortDetail, reporter, pageable
        );
    }

    // NEW: Search with isAdmin flag
    @Override
    public Page<News> getNewsByTopicOrShortDetailOrReporter(String topic, String shortDetail, String reporter, Pageable pageable, boolean isAdmin) {
        if (isAdmin) {
            return newsRepository.findByTopicIgnoreCaseContainingOrShortDetailIgnoreCaseContainingOrReporter_NameIgnoreCaseContaining(
                    topic, shortDetail, reporter, pageable
            );
        } else {
            return newsRepository.findByKeywordExcludingHidden(topic, pageable);
        }
    }

    @Override
    public Page<News> getNews(Integer pageSize, Integer page, String status) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return newsRepository.findByStatus(status, pageable);
    }

    // NEW: Get news with isAdmin flag
    @Override
    public Page<News> getNews(Integer pageSize, Integer page, String status, boolean isAdmin) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        if (isAdmin) {
            return newsRepository.findByStatus(status, pageable);
        } else {
            return newsRepository.findByStatusExcludingHidden(status, pageable);
        }
    }
}