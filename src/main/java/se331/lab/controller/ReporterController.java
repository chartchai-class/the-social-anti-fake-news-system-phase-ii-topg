package se331.lab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se331.lab.service.ReporterService;
import se331.lab.util.LabMapper;

@RestController
@RequiredArgsConstructor
public class ReporterController {

    private final ReporterService reporterService;

    @GetMapping("/reporters")
    public ResponseEntity<?> getReporters() {
        return ResponseEntity.ok(
            LabMapper.INSTANCE.getReporterDTO(reporterService.getAllReporter())
        );
    }
}
