package com.micro.flow.client;

import com.micro.flow.config.FeignConfig;
import com.micro.flow.dto.UserDtoForAccount;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@FeignClient(value = "ox-user", configuration = FeignConfig.class)
public interface UserServiceFeignClient {

    @GetMapping("/api/v1/users/for-account/{username}")
    @CircuitBreaker(name = "ox-user-get", fallbackMethod = "fallbackDtoUser")
    UserDtoForAccount getUserByUsername(@PathVariable("username") String username);

    default UserDtoForAccount fallbackDtoUser(String username, Throwable throwable) {
        return UserDtoForAccount.builder()
                .id(String.valueOf(new Random().nextInt(100)))
                .email("HIDDEDN")
                .username(username)
                .build();
    }

}
