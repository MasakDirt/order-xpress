package com.micro.flow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    @JsonProperty("access_token")
    @NotEmpty(message = "Your access token is empty. " +
            "Sorry it's our mistake we are already working on it!")
    private String accessToken;

    @JsonProperty("expires_in")
    @Min(value = 0, message = "You got negative expires time, sorry our mistake!")
    private int expiresIn;

    @JsonProperty("refresh_expires_in")
    @Min(value = 0, message = "You got negative expires refresh time, sorry our mistake!")
    private int refreshExpiresIn;

    @JsonProperty("refresh_token")
    @NotEmpty(message = "Your refresh token is empty. " +
            "Sorry it's our mistake we are already working on it!")
    private String refreshToken;

    @JsonProperty("token_type")
    @NotEmpty(message = "Your token type is empty, sorry our mistake!")
    private String tokenType;

    @JsonProperty("not-before-policy")
    private int notBeforePolicy;

    @JsonProperty("session_state")
    @NotNull(message = "Your session state is null," +
            " our mistake sorry we are working on it!")
    private UUID sessionState;

    @NotEmpty(message = "Your scope is null," +
            " our mistake sorry we are working on it!")
    private String scope;

}
