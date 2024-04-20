package com.micro.flow.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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

    @NotEmpty(message = "Role must be included")
    private String role;

}
