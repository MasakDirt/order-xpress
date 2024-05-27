package com.micro.flow.service;

import com.micro.flow.client.KeycloakLoginFeignClient;
import com.micro.flow.dto.LoginRequest;
import com.micro.flow.dto.LoginResponse;
import com.micro.flow.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class LoginServiceTests {
    @InjectMocks
    private LoginServiceImpl loginService;
    @Mock
    private KeycloakLoginFeignClient feignClient;

    @Test
    public void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("1234");
        loginRequest.setUsername("adminito25");
        LoginResponse loginResponse = new LoginResponse();

        when(feignClient.login(anyMap())).thenReturn(loginResponse);
        loginService.login(loginRequest);

        verify(feignClient, times(1)).login(anyMap());
    }

    @Test
    public void testLoginFailed() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("1234");
        loginRequest.setUsername("adminito25");

        when(feignClient.login(anyMap())).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> loginService.login(loginRequest));

        verify(feignClient, times(1)).login(anyMap());
    }
}
