package se331.lab.config;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import se331.lab.entity.News;
import se331.lab.entity.Reporter;
import se331.lab.entity.Comment;
import se331.lab.repository.CommentRepository;
import se331.lab.repository.NewsRepository;
import se331.lab.repository.ReporterRepository;
import se331.lab.security.user.Role;
import se331.lab.security.user.User;
import se331.lab.security.user.UserRepository;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    final NewsRepository newsRepository;
    final CommentRepository commentRepository;
    final UserRepository userRepository;
    final ReporterRepository reporterRepository;
    
    User user1, user2, user3; // User objects for initialization
    Reporter reporter1, reporter2;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        reporter1 = reporterRepository.save(Reporter.builder()
                .name("John Pork").build());
        reporter2 = reporterRepository.save(Reporter.builder()
                .name("Tim Cheese").build());

        // --- First news item with two comments ---
        News news1 = News.builder()
                .topic("International Peace Summit Brings World Leaders Together")
                .shortDetail("World leaders gathered for a peace summit to discuss conflict resolution strategies.")
                .longDetail("World leaders gathered this week for an International Peace Summit aimed at fostering dialogue and reducing global tensions. Delegations from over 40 nations discussed strategies for conflict resolution, humanitarian aid, and sustainable development. The summit concluded with a joint declaration emphasizing the need for diplomacy and cooperation in addressing both regional disputes and global challenges such as climate change and food security.")
                .trueVotes(131)
                .falseVotes(41)
                .date("2025-08-10")
                .time("19:00:00")
                .images(null)
                .build();

        news1.setReporter(reporter1);
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
                .trueVotes(12)
                .falseVotes(12)
                .date("2025-10-12")
                .time("10:30:00")
                .images(null)
                .build();
        news2.setReporter(reporter1);
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


        // More news to test Pagination
        News news3 = News.builder()
                .topic("Tech Giants Release New AI Tools")
                .shortDetail("Major tech companies unveil cutting-edge AI applications for businesses and consumers.")
                .longDetail("Leading technology companies have announced a suite of new AI-powered tools designed to improve productivity, automation, and user experience. These innovations range from intelligent virtual assistants to advanced data analytics platforms, promising to transform multiple industries.")
                .trueVotes(98)
                .falseVotes(150)
                .date("2025-10-20")
                .time("09:00:00")
                .images(null)
                .build();

        news3.setReporter(reporter2);
        newsRepository.save(news3);

        News news4 = News.builder()
                .topic("Global Markets React to Policy Changes")
                .shortDetail("Financial markets experience volatility following new government regulations.")
                .longDetail("Stock markets around the world saw significant fluctuations today after governments announced new fiscal and monetary policies. Investors are weighing the potential impacts on trade, inflation, and economic growth, leading to both gains and losses across sectors.")
                .trueVotes(145)
                .falseVotes(330)
                .date("2025-10-21")
                .time("14:15:00")
                .images(null)
                .build();
        news4.setReporter(reporter1);
        newsRepository.save(news4);

        News news5 = News.builder()
                .topic("Breakthrough in Cancer Research Announced")
                .shortDetail("Scientists report promising results from a new cancer therapy trial.")
                .longDetail("A team of researchers has announced a major breakthrough in cancer treatment after successful clinical trials of a new therapy. Early results show improved survival rates and fewer side effects compared to existing treatments, sparking hope for patients worldwide.")
                .trueVotes(210)
                .falseVotes(5)
                .date("2025-10-22")
                .time("11:45:00")
                .images(null)
                .build();

        news5.setReporter(reporter2);        
        newsRepository.save(news5);

        News news6 = News.builder()
                .topic("International Film Festival Opens This Week")
                .shortDetail("The annual film festival kicks off with dozens of new premieres.")
                .longDetail("Cinema enthusiasts and critics are flocking to the city for the annual international film festival. The event features over 100 films from around the world, including premieres, documentaries, and retrospectives. Directors and actors will participate in Q&A sessions and panels throughout the week.")
                .trueVotes(20)
                .falseVotes(20)
                .date("2025-10-23")
                .time("16:00:00")
                .images(null)
                .build();

        news6.setReporter(reporter2);
        newsRepository.save(news6);
// --- Initialize default users ---
        addUser(); // Call the method to add users
                reporter1.setUser(user1);

                user1.setReporter(reporter1);

                reporter2.setUser(user2);

                user2.setReporter(reporter2);
                
    }

    private void addUser() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        user1 = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .firstname("admin")
                .lastname("admin")
                .email("admin@admin.com")
                .enabled(true)
                .build();

        user2 = User.builder()
                .username("user")
                .password(encoder.encode("user"))
                .firstname("user")
                .lastname("user")
                .email("enabled@user.com")
                .enabled(true)
                .build();

        user3 = User.builder()
                .username("disableUser")
                .password(encoder.encode("disableUser"))
                .firstname("disableUser")
                .lastname("disableUser")
                .email("disableUser@user.com")
                .enabled(false)
                .build();

        user1.getRoles().add(Role.ROLE_USER);
        user1.getRoles().add(Role.ROLE_ADMIN);

        user2.getRoles().add(Role.ROLE_USER);
        user3.getRoles().add(Role.ROLE_USER);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}