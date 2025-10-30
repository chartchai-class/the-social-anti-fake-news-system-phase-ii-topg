package se331.lab.entity;

import jakarta.persistence.*;
import lombok.*;
import se331.lab.security.user.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reporter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String name;

    // One reporter can have many news items
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<News> newsList = new ArrayList<>();

    @OneToOne

    User user;
}
