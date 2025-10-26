package se331.lab.util;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import se331.lab.entity.Event;
import se331.lab.entity.EventDTO;
import se331.lab.entity.Organizer;
import se331.lab.entity.OrganizerDTO;
import se331.lab.entity.Participant;
import se331.lab.entity.ParticipantDTO;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    // Map Event -> EventDTO but ignore eventHistory in participants to prevent recursion
    @Mapping(target = "participants", qualifiedByName = "participantsShallow")
    EventDTO getEventDto(Event event);
    List<EventDTO> getEventDto(List<Event> events);

    OrganizerDTO getOrganizerDTO(Organizer organizer);
    List<OrganizerDTO> getOrganizerDTO(List<Organizer> organizers);

    ParticipantDTO getParticipantDTO(Participant participant);
    List<ParticipantDTO> getParticipantDTO(List<Participant> participants);

    // Helper mapping: shallow participant mapping (without eventHistory)
    @org.mapstruct.Named("participantsShallow")
    default List<ParticipantDTO> mapParticipantsShallow(List<Participant> participants) {
        if (participants == null) return null;
        return participants.stream()
                .map(p -> ParticipantDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .telNo(p.getTelNo())
                        .build())
                .toList();
    }
}
