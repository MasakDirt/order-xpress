package com.micro.flow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record UserResponse(Long id, String username, String email, String role) {
}
