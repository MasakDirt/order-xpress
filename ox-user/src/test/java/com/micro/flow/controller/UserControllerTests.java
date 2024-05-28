package com.micro.flow.controller;

import com.micro.flow.dto.UserDtoForAccount;
import com.micro.flow.dto.UserResponse;
import com.micro.flow.mapper.UserMapper;
import com.micro.flow.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static com.micro.flow.UserTestingUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class UserControllerTests {
    public static final String BASIC_URL = "/api/v1/users";

    private final MockMvc mockMvc;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserControllerTests(MockMvc mockMvc, UserService userService, UserMapper userMapper) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Test
    @WithMockUser(username = "adminito25")
    public void testGetUserByUsernameSuccess() throws Exception {
        String username = "adminito25";
        UserResponse expected = userMapper.getUserResponseFromDomain(
                userService.readByUsername(username));

        performGetUserByUsername(username)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "adminito25")
    public void testGetUserByUsernameForbidden() throws Exception {
        String username = "davidos";

        performGetUserByUsername(username)
                .andExpect(status().isForbidden())
                .andExpect(accessDeniedResult());
    }

    @Test
    public void testGetUserByUsernameUnauthorized() throws Exception {
        String username = "stonisloff24";

        performGetUserByUsername(username)
                .andExpect(status().isUnauthorized());
    }

    private ResultActions performGetUserByUsername(String username) throws Exception {
        return mockMvc.perform(get(BASIC_URL + "/u/{username}", username));
    }

    @Test
    @WithMockUser(username = "markiz23")
    public void testGetUserDtoForAccountSuccess() throws Exception {
        String username = "markiz23";
        UserDtoForAccount expected = userMapper.getUserDtoForAccountFromDomain(
                userService.readByUsername(username));

        mockMvc.perform(get(BASIC_URL + "/for-account/{username}", username))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "markiz23")
    public void testGetUserDtoForAccountForbidden() throws Exception {
        String username = "stonisloff24";

        mockMvc.perform(get(BASIC_URL + "/for-account/{username}", username))
                .andExpect(status().isForbidden())
                .andExpect(accessDeniedResult());
    }

    private ResultMatcher accessDeniedResult() {
        return result -> assertTrue(result.getResponse().getContentAsString()
                .contains("\"status\":\"FORBIDDEN\",\"message\":\"Access Denied\""));
    }

    @Test
    public void testGetUserDtoForAccountUnauthorized() throws Exception {
        String username = "adminito25";

        performGetUserByUsername(username)
                .andExpect(status().isUnauthorized());
    }

}
