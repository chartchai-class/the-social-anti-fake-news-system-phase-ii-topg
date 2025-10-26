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
}
