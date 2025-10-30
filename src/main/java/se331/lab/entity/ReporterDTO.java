package se331.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporterDTO {
    private Long id;
    private String name;

    // List of news items reported by this reporter
    @Builder.Default
    private List<NewsDto> newsList = new ArrayList<>();
}
