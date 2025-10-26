package se331.lab.service;

import org.springframework.data.domain.Page;
import java.util.List;
import se331.lab.entity.Organizer;

public interface OrganizerService {
    List<Organizer> getAllOrganizer();
    Page<Organizer> getOrganizer(Integer page, Integer pageSize);
}
