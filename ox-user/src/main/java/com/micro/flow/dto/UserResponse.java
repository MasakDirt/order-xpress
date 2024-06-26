package com.micro.flow.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;

    @NotEmpty(message = "Fill in your name please!")
    private String username;

    @NotNull(message = "Must be a valid e-mail address!")
    @Pattern(regexp = "[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}",
            message = "Must be a valid e-mail address!")
    private String email;

    @NotNull(message = "Your role is null, sorry it's our " +
            "mistake we are already working on it!")
    private Set<String> roles = new HashSet<>();

    private boolean isEmailVerified;
}
