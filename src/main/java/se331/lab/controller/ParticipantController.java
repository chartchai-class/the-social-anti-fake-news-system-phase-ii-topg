package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.lab.entity.ParticipantDTO;
import se331.lab.service.ParticipantService;
import se331.lab.util.LabMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipantController {

    final ParticipantService participantService;

    @GetMapping("/participants")
    public ResponseEntity<List<ParticipantDTO>> getParticipants() {
        List<ParticipantDTO> participantsDTO = LabMapper.INSTANCE.getParticipantDTO(
            participantService.getAllParticipants()
        );
        return ResponseEntity.ok(participantsDTO);
    }
}
