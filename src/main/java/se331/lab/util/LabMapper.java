package se331.lab.util;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import se331.lab.entity.*;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    // Map News -> NewsDto, include hidden
    @Mapping(source = "reporter.name", target = "reporterName")
    @Mapping(source = "hidden", target = "hidden") // 🟢 map hidden field
    NewsDto getNewsDto(News news);

    List<NewsDto> getNewsDto(List<News> newsList);

    // Map Reporter -> ReporterDTO
    @Mapping(source = "newsList", target = "newsList")
    ReporterDTO getReporterDTO(Reporter reporter);

    List<ReporterDTO> getReporterDTO(List<Reporter> reporters);

    @Mapping(target = "roles", source = "user.roles")
    ReporterAuthDTO getReporterAuthDTO(Reporter reporter);
}
