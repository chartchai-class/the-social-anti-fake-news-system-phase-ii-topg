package se331.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import se331.lab.entity.Comment;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long newsId;      
    private String text;
    private String vote;
    private Instant createdAt;
    private List<String> images;

    // Convert Comment entity to DTO
    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.newsId = comment.getNews().getId();
        this.text = comment.getText();
        this.vote = comment.getVote();
        this.createdAt = comment.getCreatedAt();
        this.images = comment.getImages();
    }
}