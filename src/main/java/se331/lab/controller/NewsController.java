package se331.lab.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import se331.lab.entity.News;
import se331.lab.repository.ReporterRepository;
import se331.lab.service.NewsService;
import se331.lab.util.LabMapper;
import se331.lab.security.user.User;
import se331.lab.entity.Reporter;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequiredArgsConstructor
public class NewsController {

    final NewsService newsService;
    final ReporterRepository reporterRepository;

    @GetMapping("news")
    public ResponseEntity<?> getNewsList(
            @RequestParam(value = "_limit", required = false) Integer perPage,
            @RequestParam(value = "_page", required = false) Integer page,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "isAdmin", required = false, defaultValue = "false") boolean isAdmin
    ) {
        perPage = (perPage == null) ? 3 : perPage;
        page = (page == null) ? 1 : page;
        status = (status == null) ? "all" : status;

        Page<News> pageOutput = newsService.getNews(perPage, page, status);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(
                LabMapper.INSTANCE.getNewsDto(pageOutput.getContent()),
                responseHeader,
                HttpStatus.OK
        );
    }


    @GetMapping("news/{id}")
    public ResponseEntity<?> getNews(@PathVariable("id") Long id) {
        News output = newsService.getNews(id);
        if (output != null) {
            return ResponseEntity.ok(LabMapper.INSTANCE.getNewsDto(output));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }

    @PatchMapping("/news/{id}/delete")
    public ResponseEntity<?> softDelete(@PathVariable Long id) {
        News deleted = newsService.softDelete(id);
        if (deleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "News not found");
        }
        return ResponseEntity.ok("News marked as deleted");
    }

    @GetMapping("news/search")
    public ResponseEntity<?> searchNews(
            @RequestParam("query") String query,
            @RequestParam(value = "_page", required = false) Integer page,
            @RequestParam(value = "_limit", required = false) Integer perPage
    ) {
        page = (page == null) ? 1 : page;
        perPage = (perPage == null) ? 3 : perPage;

        // Pageable object
        Pageable pageable = PageRequest.of(page - 1, perPage);

        // Call service method
        Page<News> pageOutput = newsService.getNews(query, pageable);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(
                LabMapper.INSTANCE.getNewsDto(pageOutput.getContent()),
                responseHeader,
                HttpStatus.OK
        );
    }


    @PostMapping("/news")
    public ResponseEntity<?> addNews(@RequestBody News news) {
        // Get currently logged-in user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Find the reporter associated with this user
        Reporter reporter = reporterRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Reporter not found for user"));

        // Set reporter on the news before saving
        news.setReporter(reporter);

        // Save news
        News output = newsService.save(news);

        return ResponseEntity.ok(LabMapper.INSTANCE.getNewsDto(output));
    }
}