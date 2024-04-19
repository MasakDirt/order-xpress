package com.micro.flow.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("ox-bag")
public interface BagServiceFeignClient {

    @PostMapping("/api/v1/my-bag/{email}")
    @CircuitBreaker(name = "ox-bag")
    void createBag(@PathVariable("email") String userEmail);

}
