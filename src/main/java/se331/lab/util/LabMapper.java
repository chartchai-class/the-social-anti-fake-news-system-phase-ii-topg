package se331.lab.util;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import se331.lab.entity.News;
import se331.lab.entity.NewsDto;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    // Map News -> NewsDto
    NewsDto getNewsDto(News news);
    List<NewsDto> getNewsDto(List<News> newsList);
}
