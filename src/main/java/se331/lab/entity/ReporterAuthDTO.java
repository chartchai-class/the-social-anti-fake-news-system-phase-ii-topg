package se331.lab.entity;


import lombok.*;
import se331.lab.security.user.Role;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporterAuthDTO {
    Long id;
    String name;
    List<Role> roles = new ArrayList<>();
}