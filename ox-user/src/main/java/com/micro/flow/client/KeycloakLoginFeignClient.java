package com.micro.flow.client;

import com.micro.flow.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "keycloak", url = "${spring.keycloak.login.uri}")
public interface KeycloakLoginFeignClient {

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    LoginResponse login(@RequestBody Map<String, ?> formParams);

}
