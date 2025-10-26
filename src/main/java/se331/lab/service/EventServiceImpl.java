package se331.lab.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import se331.lab.dao.EventDao;
import se331.lab.entity.Event;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    final EventDao eventDao;

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
        return eventDao.save(event);
    }
}
