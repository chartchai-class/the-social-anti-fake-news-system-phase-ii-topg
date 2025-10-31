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

    public String getProfileImage() {
    return profileImage;
}

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Column(length = 1000)
    private String profileImage;

    // One reporter can have many news items
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<News> newsList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    User user;
}
