package com.micro.flow.client;

import com.micro.flow.dto.clothes.ClothesResponse;
import com.micro.flow.oauth2config.FeignConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Set;

@FeignClient(value = "ox-clothes", configuration = FeignConfig.class)
public interface ClothesServiceFeignClients {

    @GetMapping("/api/v1/clothes/for-bag/ids")
    @CircuitBreaker(name = "ox-clothes-get", fallbackMethod = "fallbackClothes")
    List<ClothesResponse> getClothesByIds(@RequestBody Set<Long> ids);

    default List<ClothesResponse> fallbackClothes(Set<Long> ids, Throwable throwable) {
        return List.of(getFallbackClothesResponse(), getFallbackClothesResponse());
    }

    private ClothesResponse getFallbackClothesResponse() {
        return ClothesResponse.builder()
                .id(new Random().nextLong(100))
                .sex("HIDDEN")
                .price(BigDecimal.valueOf(-1.00))
                .availableColors(-1)
                .productName("Error Occurred")
                .availableSizes(List.of("HIDDEN", "..."))
                .build();
    }

    @GetMapping("api/v1/clothes/for-bag/total-price")
    @CircuitBreaker(name = "ox-clothes-reduce", fallbackMethod = "fallbackReduce")
    BigDecimal reduceClothes(@RequestBody Set<Long> ids);

    default BigDecimal fallbackReduce(Set<Long> ids, Throwable throwable) {
        return BigDecimal.ZERO;
    }

}
