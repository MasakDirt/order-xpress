package com.micro.flow.service.impl;

import com.micro.flow.client.KeycloakLoginFeignClient;
import com.micro.flow.dto.LoginRequest;
import com.micro.flow.dto.LoginResponse;
import com.micro.flow.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    private final KeycloakLoginFeignClient keycloakLoginFeignClient;

    @Value("${spring.keycloak.login.client-id}")
    private String clientId;

    @Value("${spring.keycloak.login.grant-type}")
    private String grantType;

    @Autowired
    public LoginServiceImpl(KeycloakLoginFeignClient keycloakLoginFeignClient) {
        this.keycloakLoginFeignClient = keycloakLoginFeignClient;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var formParams = getFormParamsForLogin(loginRequest);
        log.info("Try to get login credentials from Keycloak for {}.", loginRequest.getUsername());
        var loginResponse = keycloakLoginFeignClient.login(formParams);
        log.info("User {} successfully login.", loginRequest.getUsername());

        return loginResponse;
    }

    private Map<String, String> getFormParamsForLogin(LoginRequest loginRequest) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("client_id", clientId);
        formParams.put("username", loginRequest.getUsername());
        formParams.put("password", loginRequest.getPassword());
        formParams.put("grant_type", grantType);

        return formParams;
    }

}
