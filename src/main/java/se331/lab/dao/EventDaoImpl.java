package se331.lab.dao;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import se331.lab.entity.Event;
import se331.lab.repository.EventRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
@RequiredArgsConstructor
@Profile("db")
public class EventDaoImpl implements EventDao {
    final EventRepository eventRepository;
    @Override
    public Integer getEventSize(){
        return Math.toIntExact(eventRepository.count());
    }

    @Override
    public Page<Event> getEvents(Integer pageSize, Integer page){
        return eventRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    public Event getEvent(Long id){
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event save(Event event){
        return eventRepository.save(event);
    }

    @Override
    public Page<Event> getEvents(String title, Pageable page){
        return eventRepository.findByTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContainingOrOrganizer_NameIgnoreCaseContaining(title,title,title, page);
    }

    @Override
    public Page<Event> getEventsByTitleOrDescription(String title, String description, Pageable page) {
        return eventRepository.findByTitleContainingOrDescriptionContaining(title, description, page);
    }

    @Override
    public Page<Event> getEventsByTitleAndDescription(String title, String description, Pageable page) {
        return eventRepository.findByTitleContainingAndDescriptionContaining(title, description, page);
    }

}
