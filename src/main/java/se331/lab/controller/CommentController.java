package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * GET comments by newsId.
     * Admins can see all comments; non-admins only see non-hidden.
     */
    @GetMapping
    public List<CommentDto> getCommentsByNews(
            @RequestParam Long newsId,
            @RequestParam(required = false) Boolean admin // pass true if admin
    ) {
        List<Comment> comments = commentRepository.findByNews_Id(newsId);

        // Filter hidden for non-admins
        if (admin == null || !admin) {
            comments = comments.stream()
                    .filter(c -> c.getHidden() == null || !c.getHidden())
                    .collect(Collectors.toList());
        }

        return comments.stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    /**
     * POST new comment
     */
    @PostMapping
    public CommentDto addComment(@RequestBody CommentDto payload) {
        News news = newsRepository.findById(payload.getNewsId())
                .orElseThrow(() -> new IllegalArgumentException("News not found"));

        Comment comment = Comment.builder()
                .text(payload.getText())
                .vote(payload.getVote())
                .createdAt(payload.getCreatedAt())
                .news(news)
                .images(payload.getImages())
                .hidden(payload.getHidden() != null ? payload.getHidden() : false)
                .build();

        Comment saved = commentRepository.save(comment);
        return new CommentDto(saved);
    }

    /**
     * PATCH to toggle hidden status for admins
     */
    @PatchMapping("/hide/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CommentDto toggleHide(@PathVariable Long id, @RequestParam(required = false) Boolean hidden) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        // toggle hidden or set explicitly if param provided
        comment.setHidden(hidden != null ? hidden : !Boolean.TRUE.equals(comment.getHidden()));

        Comment saved = commentRepository.save(comment);
        return new CommentDto(saved);
    }
}
