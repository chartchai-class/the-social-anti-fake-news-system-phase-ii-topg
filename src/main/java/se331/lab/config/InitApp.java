package se331.lab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import se331.lab.entity.News;
import se331.lab.repository.NewsRepository;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    final NewsRepository newsRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        newsRepository.save(News.builder()
                .topic("International Peace Summit Brings World Leaders Together")
                .shortDetail("World leaders gathered for a peace summit to discuss conflict resolution strategies.")
                .longDetail("World leaders gathered this week for an International Peace Summit aimed at fostering dialogue and reducing global tensions. Delegations from over 40 nations discussed strategies for conflict resolution, humanitarian aid, and sustainable development. The summit concluded with a joint declaration emphasizing the need for diplomacy and cooperation in addressing both regional disputes and global challenges such as climate change and food security.")
                .trueVotes(131)
                .falseVotes(41)
                .reporter("James Smith")
                .date("2025-08-10")
                .time("19:00:00")
                .imageUrl(null)
                .build());

        newsRepository.save(News.builder()
                .topic("New Renewable Energy Plant Opens in Thailand")
                .shortDetail("A new solar power facility promises to generate clean energy for over 100,000 households.")
                .longDetail("Thailand has opened one of its largest renewable energy facilities to date. The solar power plant will help the country achieve its goal of 30% renewable energy usage by 2030. The project is expected to create hundreds of jobs and reduce annual carbon emissions by over 200,000 tons.")
                .trueVotes(215)
                .falseVotes(12)
                .reporter("Suda Rattanakorn")
                .date("2025-10-12")
                .time("10:30:00")
                .imageUrl(null)
                .build());
    }
}
