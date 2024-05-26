package com.micro.flow.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("ox-account")
public interface AccountServiceFeignClient {

    @PostMapping("/api/v1/user/{username}/account")
    @CircuitBreaker(name = "ox-account-create")
    Long createAccount(@PathVariable("username") String username);
}
