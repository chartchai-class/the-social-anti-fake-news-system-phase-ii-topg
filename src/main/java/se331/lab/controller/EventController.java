package se331.lab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import se331.lab.entity.Event;
import se331.lab.service.EventService;
import se331.lab.util.LabMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpHeaders;


@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequiredArgsConstructor
public class EventController {
    final EventService eventService;
    @GetMapping("events")
    public ResponseEntity<?> getEventLists(
            @RequestParam(value = "_limit", required = false) Integer perPage,
            @RequestParam(value = "_page", required = false) Integer page,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description
            ){

        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<Event> pageOutput;


        if (title != null && description != null) {
            //AND search
            pageOutput = eventService.getEventsByTitleAndDescription(
                    title, description, PageRequest.of(page - 1, perPage)
            );
        } else if (title != null || description != null) {
            //OR search
            String q = title != null ? title : description;
            pageOutput = eventService.getEvents(q, PageRequest.of(page - 1, perPage));
        } else {
            // Default: get all events
            pageOutput = eventService.getEvents(perPage, page);
        }


        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count",
        String.valueOf(pageOutput.getTotalElements()));
        return new
        ResponseEntity<>(LabMapper.INSTANCE.getEventDto(pageOutput.getContent()), responseHeader,HttpStatus.OK);
    }
    
    @GetMapping("events/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id){
        Event output = eventService.getEvent(id);
        if(output != null){
                return ResponseEntity.ok(LabMapper.INSTANCE.getEventDto(output));
        } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }

    @PostMapping("/events")
    public ResponseEntity<?> addEvent(@RequestBody Event event){
        Event output = eventService.save(event);
        return ResponseEntity.ok(LabMapper.INSTANCE.getEventDto(output));
    }
}

