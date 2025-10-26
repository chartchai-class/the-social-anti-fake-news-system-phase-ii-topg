package se331.lab.config;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import se331.lab.entity.News;
import se331.lab.entity.Comment;
import se331.lab.repository.CommentRepository;
import se331.lab.repository.NewsRepository;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    final NewsRepository newsRepository;
    final CommentRepository commentRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        // --- First news item with two comments ---
        News news1 = News.builder()
                .topic("International Peace Summit Brings World Leaders Together")
                .shortDetail("World leaders gathered for a peace summit to discuss conflict resolution strategies.")
                .longDetail("World leaders gathered this week for an International Peace Summit aimed at fostering dialogue and reducing global tensions. Delegations from over 40 nations discussed strategies for conflict resolution, humanitarian aid, and sustainable development. The summit concluded with a joint declaration emphasizing the need for diplomacy and cooperation in addressing both regional disputes and global challenges such as climate change and food security.")
                .trueVotes(131)
                .falseVotes(41)
                .reporter("James Smith")
                .date("2025-08-10")
                .time("19:00:00")
                .imageUrl(null)
                .build();

        newsRepository.save(news1);

        Comment comment1a = Comment.builder()
                .text("Very insightful article!")
                .vote("true")
                .createdAt(Instant.now())
                .news(news1)
                .build();

        Comment comment1b = Comment.builder()
                .text("I disagree with some points, but overall good coverage.")
                .vote("false")
                .createdAt(Instant.now())
                .news(news1)
                .build();

        commentRepository.saveAll(List.of(comment1a, comment1b));


        // --- Second news item with two comments ---
        News news2 = News.builder()
                .topic("New Renewable Energy Plant Opens in Thailand")
                .shortDetail("A new solar power facility promises to generate clean energy for over 100,000 households.")
                .longDetail("Thailand has opened one of its largest renewable energy facilities to date. The solar power plant will help the country achieve its goal of 30% renewable energy usage by 2030. The project is expected to create hundreds of jobs and reduce annual carbon emissions by over 200,000 tons.")
                .trueVotes(215)
                .falseVotes(12)
                .reporter("Suda Rattanakorn")
                .date("2025-10-12")
                .time("10:30:00")
                .imageUrl(null)
                .build();

        newsRepository.save(news2);

        Comment comment2a = Comment.builder()
                .text("Great news for the environment!")
                .vote("true")
                .createdAt(Instant.now())
                .news(news2)
                .build();

        Comment comment2b = Comment.builder()
                .text("Hope it brings enough jobs!")
                .vote("true")
                .createdAt(Instant.now())
                .news(news2)
                .build();

        commentRepository.saveAll(List.of(comment2a, comment2b));
    }
}
