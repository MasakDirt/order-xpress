package com.micro.flow.client;

import com.micro.flow.config.FeignConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(value = "ox-bag", configuration = FeignConfig.class)
public interface BagServiceFeignClient {

    @GetMapping("/api/v1/my-bag/{id}/total-price")
    @CircuitBreaker(name = "ox-bag-get-price", fallbackMethod = "fallbackTotalPrice")
    BigDecimal getBagTotalPrice(@PathVariable("id") UUID id);

    default BigDecimal fallbackTotalPrice(UUID id, Throwable throwable) {
        return BigDecimal.ZERO;
    }

    @PostMapping("/api/v1/my-bag/{id}/reset-bag")
    @CircuitBreaker(name = "ox-bag-reset-clothes")
    void resetClothes(@PathVariable("id") UUID id);

}
