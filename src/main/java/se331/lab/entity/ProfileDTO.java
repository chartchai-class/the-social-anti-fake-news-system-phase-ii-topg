package se331.lab.entity;

import lombok.Builder;
import lombok.Data;
import se331.lab.security.user.Role;

import java.util.List;

@Data
@Builder
public class ProfileDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private List<Role> roles;
    private String profileImage;
}

