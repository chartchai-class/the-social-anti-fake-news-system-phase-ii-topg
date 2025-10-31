package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import se331.lab.entity.Comment;
import se331.lab.entity.CommentDto;
import se331.lab.entity.News;
import se331.lab.repository.CommentRepository;
import se331.lab.repository.NewsRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    // GET comments by newsId using DTO
    @GetMapping
    public List<CommentDto> getCommentsByNews(@RequestParam Long newsId) {
        return commentRepository.findByNews_Id(newsId)
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    // POST new comment using DTO
    @PostMapping
    public CommentDto addComment(@RequestBody CommentDto payload) {
        News news = newsRepository.findById(payload.getNewsId())
                .orElseThrow(() -> new IllegalArgumentException("News not found"));

        Comment comment = Comment.builder()
                .text(payload.getText())
                .vote(payload.getVote())
                .createdAt(payload.getCreatedAt())
                .news(news)
                .images(payload.getImages())  // store images here
                .build();

        Comment saved = commentRepository.save(comment);
        return new CommentDto(saved);
    }
}
