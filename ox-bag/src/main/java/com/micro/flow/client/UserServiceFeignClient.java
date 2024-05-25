package com.micro.flow.client;

import com.micro.flow.dto.user.UserResponse;
import com.micro.flow.oauth2config.FeignConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "ox-user", configuration = FeignConfig.class)
public interface UserServiceFeignClient {

    @GetMapping("/api/v1/users/u/{username}")
    @CircuitBreaker(name = "ox-user-get", fallbackMethod = "fallbackUser")
    UserResponse getByUsername(@PathVariable("username") String username);

    default UserResponse fallbackUser(String username, Throwable throwable) {
        return UserResponse.builder()
                .email("EMAIL")
                .roles(Set.of("HIDDEN"))
                .username(username)
                .id("HIDDEN")
                .build();
    }

}
