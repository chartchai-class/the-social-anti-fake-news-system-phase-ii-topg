package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import se331.lab.entity.ProfileDTO;
import se331.lab.security.user.User;
import se331.lab.security.user.UserRepository;
import se331.lab.security.user.Role;
import se331.lab.entity.ReporterAuthDTO;
import se331.lab.repository.ReporterRepository;
import se331.lab.entity.Reporter;
import se331.lab.controller.SupabaseController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProfileController {

    private final UserRepository userRepository;
    private final ReporterRepository reporterRepository;
    private final se331.lab.util.SupabaseStorageService supabaseStorageService;

    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileDTO dto = ProfileDTO.builder()
                .id(user.getId().longValue())
                .name(user.getReporter() != null
                        ? user.getReporter().getName()           // prefer reporter name
                        : user.getFirstname() + " " + user.getLastname())
                .email(user.getEmail())                        // keep email from User
                .username(user.getUsername())                  // keep username from User
                .roles(user.getRoles() == null ? List.of() : user.getRoles())
                .profileImage(user.getReporter() != null ? user.getReporter().getProfileImage() : null)
                .build();

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        try {
            // 1️⃣ Find the current user
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Reporter reporter = reporterRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Reporter not found"));

            // 2️⃣ Upload image to Supabase via the service directly
            String imageUrl = supabaseStorageService.uploadFile(file);

            // 3️⃣ Save the URL in the reporter
            reporter.setProfileImage(imageUrl);
            reporterRepository.save(reporter);

            // 4️⃣ Return response
            return ResponseEntity.ok(Map.of("imageUrl", imageUrl));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
