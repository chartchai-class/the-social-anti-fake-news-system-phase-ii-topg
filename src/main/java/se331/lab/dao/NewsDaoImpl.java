package se331.lab.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        return newsRepository.findByTopicIgnoreCaseContainingOrShortDetailIgnoreCaseContainingOrReporterIgnoreCaseContaining(
                topic, shortDetail, reporter, pageable
        );
    }
}
