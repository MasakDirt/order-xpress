package com.micro.flow.controller;

import com.micro.flow.domain.Role;
import com.micro.flow.dto.LoginRequest;
import com.micro.flow.dto.UserCreateRequest;
import com.micro.flow.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.micro.flow.DomainTestingUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class AuthControllerTests {
    public static final String BASIC_URL = "/api/v1/auth";

    private final MockMvc mockMvc;

    @Autowired
    public AuthControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testLoginSuccess() throws Exception {
        LoginRequest loginRequest = new LoginRequest("adminito25", "1234");

        performLogin(loginRequest)
                .andExpect(status().isOk())
                .andExpectAll(result -> assertTrue(result.getResponse().getContentAsString()
                                .contains("\"scope\":\"profile email microprofile-jwt\"")),
                        result -> assertFalse(result.getResponse().getContentAsString()
                                .contains("\"access_token\":\"null\"")),
                        result -> assertTrue(result.getResponse().getContentAsString()
                                .contains("\"expires_in\":300,\"refresh_expires_in\":1800")),
                        result -> assertTrue(result.getResponse().getContentAsString()
                                .contains("\"token_type\":\"Bearer\",\"not-before-policy\":0,")),
                        result -> assertFalse(result.getResponse().getContentAsString()
                                .contains("\"session_state\":\"null\"")),
                        result -> assertFalse(result.getResponse().getContentAsString()
                                .contains("\"refresh_token\":\"null\"")));
    }

    @Test
    public void testLoginInvalidUsername() throws Exception {
        LoginRequest loginRequest = new LoginRequest("invalid", "1234");

        performLogin(loginRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"BAD_REQUEST\"," + "\"message\":\"Write please valid" +
                                " credentials [401 Unauthorized] during [POST] to")));
    }

    @Test
    public void testLoginInvalidPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest("adminito25", "invalid password");

        performLogin(loginRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"BAD_REQUEST\",\"message\":\"Write please valid" +
                                " credentials [401 Unauthorized] during [POST] to")));
    }

    private ResultActions performLogin(LoginRequest loginRequest) throws Exception {
        return mockMvc.perform(post(BASIC_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequest)));
    }

    @Test
    public void testSignUpSuccess() throws Exception {
        String email = "test@mail.co";
        String username = "user";
        String password = "password";

        UserCreateRequest createRequest = new UserCreateRequest(username, email, password);
        UserResponse expected = UserResponse.builder()
                .isEmailVerified(true)
                .email(email)
                .username(username)
                .roles(Set.of(new Role("ox_user").getName()))
                .build();

        performCreate(createRequest)
                .andExpect(status().isCreated())
                .andExpect(result -> assertEquals(asJsonString(expected).substring(11),
                        result.getResponse().getContentAsString().substring(45)));
    }

    @Test
    public void testSignUpInvalidEmail() throws Exception {
        String email = "justemail";
        String username = "testeduser";
        String password = "password";
        UserCreateRequest createRequest = new UserCreateRequest(username, email, password);

        performCreate(createRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"BAD_REQUEST\"," +
                                "\"message\":\"Must be a valid e-mail address\"")));
    }

    private ResultActions performCreate(UserCreateRequest createRequest) throws Exception {
        return mockMvc.perform(post(BASIC_URL + "/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createRequest)));
    }

}
