package se331.lab.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import se331.lab.entity.News;
import se331.lab.service.NewsService;
import se331.lab.util.LabMapper;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequiredArgsConstructor
public class NewsController {

    final NewsService newsService;

    @GetMapping("news")
    public ResponseEntity<?> getNewsList(
            @RequestParam(value = "_limit", required = false) Integer perPage,
            @RequestParam(value = "_page", required = false) Integer page,
            @RequestParam(value = "topic", required = false) String topic,
            @RequestParam(value = "reporter", required = false) String reporter
    ) {
        perPage = (perPage == null) ? 3 : perPage;
        page = (page == null) ? 1 : page;

        Page<News> pageOutput = newsService.getNews(perPage, page);

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
        News output = newsService.save(news);
        return ResponseEntity.ok(LabMapper.INSTANCE.getNewsDto(output));
    }
}
