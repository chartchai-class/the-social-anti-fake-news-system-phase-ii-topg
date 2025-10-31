package se331.lab.entity;

import java.util.List;
import lombok.Data;

@Data
public class NewsDto {
    Long id;
    String topic;
    String shortDetail;
    String longDetail;
    Integer trueVotes;
    Integer falseVotes;
    String reporterName;
    String date;
    String time;
    List<String> images;

    // 🟢 Optional: include hidden status
    Boolean hidden;
}
