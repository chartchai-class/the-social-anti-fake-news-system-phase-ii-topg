package se331.lab.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import se331.lab.dao.EventDao;
import se331.lab.dao.OrganizerDao;
import se331.lab.entity.Event;
import se331.lab.entity.Organizer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    final EventDao eventDao;
    final OrganizerDao organizerDao;

    @Override
    public Integer getEventSize(){
        return eventDao.getEventSize();
    }

    @Override
    public Page<Event> getEvents(Integer pageSize, Integer page){
        return eventDao.getEvents(pageSize, page);
    }

    @Override
    public Event getEvent(Long id){
        return eventDao.getEvent(id);
    }

    @Override
    @Transactional
    public Event save(Event event){
        Organizer organizer =
        organizerDao.findById(event.getOrganizer().getId()).orElse(null);
        event.setOrganizer(organizer);
        organizer.getOwnEvents().add(event);
        return eventDao.save(event);
    }

    @Override
    public Page<Event> getEvents(String title, Pageable pageable){
        return eventDao.getEvents(title, pageable);
    }

    @Override
    public Page<Event> getEventsByTitleOrDescription(String title, String description, Pageable pageable) {
        return eventDao.getEventsByTitleOrDescription(title, description, pageable);
    }

    @Override
    public Page<Event> getEventsByTitleAndDescription(String title, String description, Pageable pageable) {
        return eventDao.getEventsByTitleAndDescription(title, description, pageable);
    }
}
