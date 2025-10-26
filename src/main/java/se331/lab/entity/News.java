package se331.lab.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String topic;
    @Column(length = 2000)
    String shortDetail;

    @Column(length = 5000)
    String longDetail;

    Integer trueVotes;
    Integer falseVotes;
    String reporter;
    String date;
    String time;
    String imageUrl;

    // One news can have many comments
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
