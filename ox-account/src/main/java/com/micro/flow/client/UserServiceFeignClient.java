package com.micro.flow.client;

import com.micro.flow.dto.UserDtoForAccount;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@FeignClient("ox-user")
public interface UserServiceFeignClient {

    @GetMapping("/api/v1/users/{username}")
    @CircuitBreaker(name = "ox-user-get", fallbackMethod = "fallbackDtoUser")
    UserDtoForAccount getUserByUsername(@PathVariable("username") String username);

    default UserDtoForAccount fallbackDtoUser(String username, Throwable throwable) {
        return UserDtoForAccount.builder()
                .userId(new Random().nextLong(100))
                .email("HIDDEDN")
                .username(username)
                .build();
    }

}
