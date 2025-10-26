package se331.lab.dao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import se331.lab.entity.Event;

public interface EventDao {
    Page<Event> getEvents(Integer pageSize, Integer page);
    Event getEvent(Long id);
    Event save(Event event);
    Page<Event> getEvents(String name, Pageable page);
    Page<Event> getEventsByTitleOrDescription(String title, String description, Pageable page);
    Page<Event> getEventsByTitleAndDescription(String title, String description, Pageable page);
    Integer getEventSize();
}
