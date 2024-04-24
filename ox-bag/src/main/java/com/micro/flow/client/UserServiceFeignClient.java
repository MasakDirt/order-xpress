package com.micro.flow.client;

import com.micro.flow.dto.user.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ox-user")
public interface UserServiceFeignClient {

    @GetMapping("/api/v1/users/e/{email}")
    @CircuitBreaker(name = "ox-user", fallbackMethod = "fallbackUser")
    UserResponse getByEmail(@PathVariable("email") String email);

    private UserResponse fallbackUser(String userEmail, Throwable throwable) {
        return UserResponse.builder()
                .email(userEmail)
                .role("HIDDEN")
                .username("USERNAME")
                .id(0L)
                .build();
    }

}
