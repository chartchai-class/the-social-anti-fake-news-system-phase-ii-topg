package se331.lab.config;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import lombok.RequiredArgsConstructor;
import se331.lab.entity.Event;
import se331.lab.entity.Organizer;
import se331.lab.entity.Participant;
import se331.lab.repository.EventRepository;
import se331.lab.repository.OrganizerRepository;
import se331.lab.repository.ParticipantRepository;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent>{
    @Autowired
    final EventRepository eventRepository;
    final OrganizerRepository organizerRepository;
    final ParticipantRepository participantRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent){
        Organizer org1, org2, org3;
        org1 = organizerRepository.save(Organizer.builder()
            .name("CAMT").build());
        org2 = organizerRepository.save(Organizer.builder()
            .name("CMU").build());
        org3 = organizerRepository.save(Organizer.builder()
            .name("ChiangMai").build());

        Event tempEvent;
        tempEvent = eventRepository.save(Event.builder()
            .category("Academic")
            .title("Midterm Exam")
            .description("A time for taking the exam")
            .location("CAMT Building")
            .date("3rd Sept")
            .time("3.00-4.00 pm.")
            .petsAllowed(false)
            .build());
        tempEvent.setOrganizer(org1);
        org1.getOwnEvents().add(tempEvent);

        tempEvent = eventRepository.save(Event.builder()
            .category("Academic")
            .title("Commencement Day")
            .description("A time for celebration")
            .location("CMU Convention hall")
            .date("21th Jan")
            .time("8.00am-4.00 pm.")
            .petsAllowed(false)
            .build()        
        );
        tempEvent.setOrganizer(org1);
        org1.getOwnEvents().add(tempEvent);

        tempEvent = eventRepository.save(Event.builder()
            .category("Cultural")
            .title("Loy Kratong")
            .description("A time for Krathong")
            .location("Ping River")
            .date("21th Nov")
            .time("8.00-10.00 pm.")
            .petsAllowed(false)
            .build()       
        );
        tempEvent.setOrganizer(org2);
        org2.getOwnEvents().add(tempEvent);

        tempEvent = eventRepository.save(Event.builder()
            .category("Cultural")
            .title("Songkran")
            .description("Let's Play Water")
            .location("Chiang Mai Moat")
            .date("13th April")
            .time("10.00am - 6.00 pm.")
            .petsAllowed(true)
            .build()    
        );
        tempEvent.setOrganizer(org3);
        org3.getOwnEvents().add(tempEvent);

        // --- Add participants ---
        Participant p1 = Participant.builder().name("Alice").telNo("0811111111").build();
        Participant p2 = Participant.builder().name("Bob").telNo("0822222222").build();
        Participant p3 = Participant.builder().name("Charlie").telNo("0833333333").build();
        Participant p4 = Participant.builder().name("David").telNo("0844444444").build();
        Participant p5 = Participant.builder().name("Eve").telNo("0855555555").build();

        // Save participants first (you'll need ParticipantRepository injected)
        p1 = participantRepository.save(p1);
        p2 = participantRepository.save(p2);
        p3 = participantRepository.save(p3);
        p4 = participantRepository.save(p4);
        p5 = participantRepository.save(p5);

        // Assign participants to events (mutable lists!)
        tempEvent = org1.getOwnEvents().get(0); // Midterm Exam
        tempEvent.setParticipants(new ArrayList<>(List.of(p1, p2, p3)));

        tempEvent = org1.getOwnEvents().get(1); // Commencement Day
        tempEvent.setParticipants(new ArrayList<>(List.of(p2, p3, p4)));

        tempEvent = org2.getOwnEvents().get(0); // Loy Kratong
        tempEvent.setParticipants(new ArrayList<>(List.of(p1, p2, p4, p5)));

        tempEvent = org3.getOwnEvents().get(0); // Songkran
        tempEvent.setParticipants(new ArrayList<>(List.of(p1, p3, p5)));

        // Also update participants' eventHistory
        p1.setEventHistory(new ArrayList<>(List.of(org1.getOwnEvents().get(0), org2.getOwnEvents().get(0), org3.getOwnEvents().get(0))));
        p2.setEventHistory(new ArrayList<>(List.of(org1.getOwnEvents().get(0), org1.getOwnEvents().get(1), org2.getOwnEvents().get(0))));
        p3.setEventHistory(new ArrayList<>(List.of(org1.getOwnEvents().get(0), org1.getOwnEvents().get(1), org3.getOwnEvents().get(0))));
        p4.setEventHistory(new ArrayList<>(List.of(org1.getOwnEvents().get(1), org2.getOwnEvents().get(0))));
        p5.setEventHistory(new ArrayList<>(List.of(org2.getOwnEvents().get(0), org3.getOwnEvents().get(0))));

        // Save events again with participants
        for (Organizer org : List.of(org1, org2, org3)) {
            for (Event e : org.getOwnEvents()) {
                eventRepository.save(e);
            }
        }

        // Save participants with updated eventHistory
        for (Participant p : List.of(p1, p2, p3, p4, p5)) {
            participantRepository.save(p);
        }

    }
}
