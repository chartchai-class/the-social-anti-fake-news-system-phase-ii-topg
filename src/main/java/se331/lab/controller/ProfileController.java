package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se331.lab.entity.ProfileDTO;
import se331.lab.security.user.User;
import se331.lab.security.user.UserRepository;
import se331.lab.security.user.Role;
import se331.lab.entity.ReporterAuthDTO;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProfileController {

    private final UserRepository userRepository;

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
                .build();

        return ResponseEntity.ok(dto);
    }
}
