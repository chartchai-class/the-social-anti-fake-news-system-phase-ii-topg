package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import se331.lab.security.user.User;
import se331.lab.security.user.UserRepository;
import se331.lab.security.user.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReporterAdminController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<se331.lab.entity.ReporterAuthDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> se331.lab.entity.ReporterAuthDTO.builder()
                        .id(user.getId().longValue())
                        .name(user.getReporter() != null
                                ? user.getReporter().getName()
                                : user.getFirstname() + " " + user.getLastname())
                        .roles(user.getRoles() == null ? List.of() : user.getRoles())
                        .build())
                .toList();
    }

    // 🧩 PATCH /admin/users/{id}/promote?role=ROLE_ADMIN
    @PatchMapping("/users/{id}/promote")
    public ResponseEntity<?> promoteUser(@PathVariable Integer id, @RequestParam String role) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Role targetRole;
        try {
            // Convert string like "ROLE_ADMIN" to enum Role.ROLE_ADMIN
            targetRole = Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role: " + role);
        }

        // Initialize if null
        if (user.getRoles() == null) {
            user.setRoles(List.of(targetRole));
        } else if (!user.getRoles().contains(targetRole)) {
            // Add the new role
            user.getRoles().add(targetRole);
        } else {
            return ResponseEntity.ok("User already has role: " + role);
        }

        userRepository.save(user);
        return ResponseEntity.ok("User promoted to " + role);
    }
}
