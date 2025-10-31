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
    Reporter reporter1, reporter2,reporter3;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        reporter1 = reporterRepository.save(Reporter.builder()
                .name("John Pork").build());
        reporter2 = reporterRepository.save(Reporter.builder()
                .name("Tim Cheese").build());
        reporter3 = reporterRepository.save(Reporter.builder()
                .name("Simon Claw").build());

        // --- Create reporters and assign users immediately ---
        reporter1 = Reporter.builder()
                .name("John Pork")
                .user(user1)
                .build();

        reporter2 = Reporter.builder()
                .name("Tim Cheese")
                .user(user2)
                .build();

        reporterRepository.saveAll(List.of(reporter1, reporter2));

        // --- First news item ---
        News news1 = News.builder()
                .topic("International Peace Summit Brings World Leaders Together")
                .shortDetail("World leaders gathered for a peace summit to discuss conflict resolution strategies.")
                .longDetail("World leaders gathered this week for an International Peace Summit aimed at fostering dialogue and reducing global tensions. Delegations from over 40 nations discussed strategies for conflict resolution, humanitarian aid, and sustainable development. The summit concluded with a joint declaration emphasizing the need for diplomacy and cooperation in addressing both regional disputes and global challenges such as climate change and food security.")
                .trueVotes(131)
                .falseVotes(41)
                .date("2025-08-10")
                .time("19:00:00")
                .images(null)
                .reporter(reporter1)
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
Comment comment1c = Comment.builder()
                .text("This is exactly what I needed to read today. Great perspective!")
                .vote("true")
                .createdAt(Instant.now())
                .news(news1)
                .build();
Comment comment1d = Comment.builder()
                .text("The analysis could have gone deeper into the underlying issues.")
                .vote("false")
                .createdAt(Instant.now())
                .news(news1)
                .build();
Comment comment1e = Comment.builder()
                .text("Well-researched and balanced reporting. Thank you for this.")
                .vote("true")
                .createdAt(Instant.now())
                .news(news1)
                .build();
Comment comment1f = Comment.builder()
                .text("I think the article missed some important context about the situation.")
                .vote("false")
                .createdAt(Instant.now())
                .news(news1)
                .build();
Comment comment1g = Comment.builder()
                .text("Finally, someone is talking about this! More coverage needed.")
                .vote("true")
                .createdAt(Instant.now())
                .news(news1)
                .build();
Comment comment1h = Comment.builder()
                .text("Interesting read, though I would have liked to see more data included.")
                .vote("true")
                .createdAt(Instant.now())
                .news(news1)
                .build();
Comment comment1i = Comment.builder()
                .text("This seems biased. The other side of the story wasn't fairly represented.")
                .vote("false")
                .createdAt(Instant.now())
                .news(news1)
                .build();
Comment comment1j = Comment.builder()
                .text("Shared this with my colleagues. Everyone should read this!")
                .vote("true")
                .createdAt(Instant.now())
                .news(news1)
                .build();
commentRepository.saveAll(List.of(comment1a, comment1b, comment1c, comment1d, comment1e, comment1f, comment1g, comment1h, comment1i, comment1j));

        // --- Second news item ---
        News news2 = News.builder()
                .topic("New Renewable Energy Plant Opens in Thailand")
                .shortDetail("A new solar power facility promises to generate clean energy for over 100,000 households.")
                .longDetail("Thailand has opened one of its largest renewable energy facilities to date. The solar power plant will help the country achieve its goal of 30% renewable energy usage by 2030. The project is expected to create hundreds of jobs and reduce annual carbon emissions by over 200,000 tons.")
                .trueVotes(12)
                .falseVotes(12)
                .date("2025-10-12")
                .time("10:30:00")
                .images(null)
                .reporter(reporter1)
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

        // --- More news items ---
        News news3 = News.builder()
                .topic("Tech Giants Release New AI Tools")
                .shortDetail("Major tech companies unveil cutting-edge AI applications for businesses and consumers.")
                .longDetail("Leading technology companies have announced a suite of new AI-powered tools designed to improve productivity, automation, and user experience. These innovations range from intelligent virtual assistants to advanced data analytics platforms, promising to transform multiple industries.")
                .trueVotes(98)
                .falseVotes(150)
                .date("2025-10-20")
                .time("09:00:00")
                .images(null)
                .reporter(reporter2)
                .build();
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
                .reporter(reporter1)
                .build();
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
                .reporter(reporter2)
                .build();
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
                .reporter(reporter2)
                .build();
        newsRepository.save(news6);

        News news7 = News.builder()
                .topic("Major Tech Company Announces AI Breakthrough")
                .shortDetail("Revolutionary language model promises human-level reasoning capabilities.")
                .longDetail("A leading technology firm unveiled its latest artificial intelligence system today, claiming significant advances in logical reasoning and problem-solving. The new model reportedly outperforms previous systems on complex mathematical proofs and scientific analysis. Industry experts are calling it a potential game-changer for AI applications in research and development.")
                .trueVotes(45)
                .falseVotes(12)
                .date("2025-10-29")
                .time("09:30:00")
                .images(null)
                .reporter(reporter1)
                .build();
        newsRepository.save(news7);

News news8 = News.builder()
                .topic("Local School District Proposes Extended Learning Program")
                .shortDetail("New initiative aims to provide after-school tutoring and enrichment activities.")
                .longDetail("The school board voted unanimously to approve funding for an expanded after-school program beginning next semester. The initiative will offer free tutoring in core subjects, arts and music classes, and STEM activities for students in grades K-8. Parents have expressed strong support for the program, which addresses concerns about learning gaps and childcare needs.")
                .trueVotes(67)
                .falseVotes(8)
                .date("2025-10-28")
                .time("18:45:00")
                .images(null)
                .reporter(reporter3)
                .build();
        newsRepository.save(news8);

News news9 = News.builder()
                .topic("Rare Species of Whale Spotted Off Coastal Waters")
                .shortDetail("Marine biologists celebrate sighting of endangered blue whale.")
                .longDetail("A team of marine researchers documented a rare blue whale approximately 15 miles offshore yesterday morning. The sighting marks only the third confirmed appearance of the species in these waters in the past decade. Conservationists view this as a positive sign for ocean health and whale population recovery efforts following decades of protection.")
                .trueVotes(89)
                .falseVotes(3)
                .date("2025-10-27")
                .time("11:20:00")
                .images(null)
                .reporter(reporter2)
                .build();
        newsRepository.save(news9);

News news10 = News.builder()
                .topic("Downtown Construction Project Faces Delays")
                .shortDetail("New transit hub completion pushed back six months due to supply issues.")
                .longDetail("City officials announced that the highly anticipated downtown transit center will not open until next summer. The delay is attributed to supply chain disruptions affecting steel and electrical components. The project, initially budgeted at $120 million, may see cost overruns of up to 15%. Commuters and local businesses have voiced frustration over the extended timeline.")
                .trueVotes(34)
                .falseVotes(28)
                .date("2025-10-26")
                .time("14:15:00")
                .images(null)
                .reporter(reporter1)
                .build();
        newsRepository.save(news10);

News news11 = News.builder()
                .topic("Community Garden Initiative Transforms Vacant Lot")
                .shortDetail("Neighbors unite to create green space in urban neighborhood.")
                .longDetail("What was once an abandoned lot is now a thriving community garden thanks to local residents who organized cleanup efforts and secured city permits. The space now features 40 raised beds, a composting area, and benches for gathering. Organizers say the garden has brought neighbors together and provides fresh produce for families in the area.")
                .trueVotes(102)
                .falseVotes(5)
                .date("2025-10-25")
                .time("10:00:00")
                .images(null)
                .reporter(reporter3)
                .build();
        newsRepository.save(news11);

News news12 = News.builder()
                .topic("Hospital Opens New Cancer Treatment Center")
                .shortDetail("State-of-the-art facility offers advanced radiation therapy options.")
                .longDetail("Regional Medical Center celebrated the grand opening of its comprehensive cancer care wing today. The 50,000 square-foot facility houses the latest in radiation therapy equipment, including proton beam technology. It also features private consultation rooms, a wellness center, and support group spaces. Oncologists estimate the center will serve over 2,000 patients annually.")
                .trueVotes(78)
                .falseVotes(6)
                .date("2025-10-24")
                .time("13:30:00")
                .images(null)
                .reporter(reporter2)
                .build();
        newsRepository.save(news12);

News news13 = News.builder()
                .topic("Local Restaurant Wins National Culinary Award")
                .shortDetail("Chef Maria Rodriguez recognized for innovative farm-to-table cuisine.")
                .longDetail("A hometown restaurant has earned top honors at the National Restaurant Association's annual awards ceremony. Chef Rodriguez's seasonal menu and commitment to sourcing ingredients from local farms impressed judges. The restaurant has seen a surge in reservations since the announcement, with wait times now extending several weeks for prime dinner slots.")
                .trueVotes(56)
                .falseVotes(4)
                .date("2025-10-22")
                .time("19:00:00")
                .images(null)
                .reporter(reporter1)
                .build();
        newsRepository.save(news13);

News news14 = News.builder()
                .topic("Study Links Exercise to Improved Cognitive Function")
                .shortDetail("University research shows 30 minutes daily activity boosts memory.")
                .longDetail("Researchers at the University Medical School published findings from a five-year study involving 3,000 participants. Results indicate that regular moderate exercise significantly improves memory retention and cognitive processing speed, particularly in adults over 50. The study adds to growing evidence supporting physical activity as a key factor in preventing age-related cognitive decline.")
                .trueVotes(91)
                .falseVotes(15)
                .date("2025-10-21")
                .time("08:00:00")
                .images(null)
                .reporter(reporter3)
                .build();
        newsRepository.save(news14);

News news15 = News.builder()
                .topic("Electric Vehicle Sales Surge in Third Quarter")
                .shortDetail("EV purchases up 40% compared to same period last year.")
                .longDetail("The automotive industry is experiencing a dramatic shift as electric vehicle adoption accelerates nationwide. Analysts attribute the growth to improved battery range, expanded charging infrastructure, and new tax incentives. Major manufacturers report waiting lists for popular models, while dealers are increasing their EV inventory to meet demand.")
                .trueVotes(72)
                .falseVotes(31)
                .date("2025-10-20")
                .time("15:45:00")
                .images(null)
                .reporter(reporter2)
                .build();
        newsRepository.save(news15);

News news16 = News.builder()
                .topic("Historic Theater Undergoes Restoration")
                .shortDetail("Century-old venue to reopen next spring with modern amenities.")
                .longDetail("The beloved Grand Theater, which closed two years ago due to structural concerns, is midway through an extensive renovation project. Workers are carefully restoring original architectural details while adding updated seating, acoustics, and accessibility features. The $8 million project is funded through a combination of private donations and historic preservation grants.")
                .trueVotes(43)
                .falseVotes(7)
                .date("2025-10-19")
                .time("12:00:00")
                .images(null)
                .reporter(reporter1)
                .build();
        newsRepository.save(news16);

News news17 = News.builder()
                .topic("Youth Soccer League Expands to Include All Skill Levels")
                .shortDetail("New recreational division ensures every child can participate.")
                .longDetail("In response to parent feedback, the youth soccer association has launched a non-competitive division focused on skill development and fun rather than winning. The program has already enrolled 200 children who previously felt intimidated by competitive play. Coaches emphasize teamwork, individual improvement, and positive experiences for all participants.")
                .trueVotes(85)
                .falseVotes(9)
                .date("2025-10-18")
                .time("17:30:00")
                .images(null)
                .reporter(reporter3)
                .build();
        newsRepository.save(news17);

News news18 = News.builder()
                .topic("Cybersecurity Firm Detects New Phishing Campaign")
                .shortDetail("Experts warn of sophisticated email scam targeting small businesses.")
                .longDetail("A cybersecurity company has identified a widespread phishing operation that mimics legitimate vendor invoices and payment requests. The scam has already cost businesses an estimated $2 million nationwide. Security experts recommend employee training on identifying suspicious emails, implementing two-factor authentication, and verifying payment requests through secondary channels.")
                .trueVotes(38)
                .falseVotes(11)
                .date("2025-10-17")
                .time("09:15:00")
                .images(null)
                .reporter(reporter2)
                .build();
        newsRepository.save(news18);

News news19 = News.builder()
                .topic("City Park to Feature New Inclusive Playground")
                .shortDetail("Accessible equipment allows children of all abilities to play together.")
                .longDetail("Construction begins next month on a fully accessible playground at Riverside Park. The design includes wheelchair-accessible ramps, sensory play elements, and equipment suitable for children with various physical and developmental needs. The project was championed by parents of children with disabilities who sought a space where all kids could play side by side.")
                .trueVotes(118)
                .falseVotes(2)
                .date("2025-10-16")
                .time("11:45:00")
                .images(null)
                .reporter(reporter1)
                .build();
        newsRepository.save(news19);

News news20 = News.builder()
                .topic("Coffee Shop Chain Switches to Compostable Packaging")
                .shortDetail("Environmental initiative eliminates 5 million plastic cups annually.")
                .longDetail("A popular coffee chain announced a complete transition to compostable cups, lids, and straws across all locations. The company partnered with industrial composting facilities to ensure proper disposal. Environmental groups have praised the move as a significant step toward reducing single-use plastic waste, though some customers report the new materials feel different.")
                .trueVotes(94)
                .falseVotes(22)
                .date("2025-10-15")
                .time("07:30:00")
                .images(null)
                .reporter(reporter3)
                .build();
        newsRepository.save(news20);
// --- Initialize default users ---
        addUser(); // Call the method to add users
                reporter1.setUser(user1);

                user1.setReporter(reporter1);

                reporter2.setUser(user2);

                user2.setReporter(reporter2);

                reporter3.setUser(user3);

                user3.setReporter(reporter3);
                
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
                .username("reporter")
                .password(encoder.encode("reporter"))
                .firstname("reporter")
                .lastname("reporter")
                .email("enabled@reporter.com")
                .enabled(true)
                .build();

        user3 = User.builder()
                .username("reader")
                .password(encoder.encode("reader"))
                .firstname("reader")
                .lastname("reader")
                .email("enabled@reader.com")
                .enabled(true)
                .build();

        user1.getRoles().add(Role.ROLE_REPORTER);
        user1.getRoles().add(Role.ROLE_READER);
        user1.getRoles().add(Role.ROLE_ADMIN);

        user2.getRoles().add(Role.ROLE_REPORTER);
        user2.getRoles().add(Role.ROLE_READER);

        user3.getRoles().add(Role.ROLE_READER);

        userRepository.saveAll(List.of(user1, user2, user3));
    }
}
