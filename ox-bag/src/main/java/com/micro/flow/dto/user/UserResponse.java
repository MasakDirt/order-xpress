package com.micro.flow.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    @NotEmpty(message = "Username must be included")
    private String username;

    @NotEmpty(message = "Email must be included")
    private String email;

    private Set<String> roles;

}
