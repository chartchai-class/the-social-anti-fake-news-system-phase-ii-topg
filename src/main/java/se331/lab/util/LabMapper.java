package se331.lab.util;

import java.util.List;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;
import se331.lab.entity.Event;
import se331.lab.entity.EventDTO;
import se331.lab.entity.News;
import se331.lab.entity.NewsDto;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    // Map Event -> EventDTO but ignore eventHistory in participants to prevent recursion
    EventDTO getEventDto(Event event);
    List<EventDTO> getEventDto(List<Event> events);

    // Existing event mappings…
    List<NewsDto> getNewsDto(List<News> newsList);
    NewsDto getNewsDto(News news);
}
