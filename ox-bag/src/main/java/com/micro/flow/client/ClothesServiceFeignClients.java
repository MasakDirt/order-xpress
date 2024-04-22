package com.micro.flow.client;

import com.micro.flow.dto.clothes.ClothesResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Set;

@FeignClient("ox-clothes")
public interface ClothesServiceFeignClients {

    @GetMapping("/api/v1/clothes/for-bag/{ids}")
    @CircuitBreaker(name = "ox-clothes", fallbackMethod = "fallbackClothes")
    List<ClothesResponse> getClothesByIds(@PathVariable("ids") Set<Long> ids);

    default List<ClothesResponse> fallbackClothes(Set<Long> ids, Throwable throwable) {
        return List.of(getFallbackClothesResponse(ids), getFallbackClothesResponse(ids));
    }

    private ClothesResponse getFallbackClothesResponse(Set<Long> ids) {
        return ClothesResponse.builder()
                .id(new Random().nextLong(ids.size() - 1))
                .sex("HIDDEN")
                .price(BigDecimal.valueOf(-1.00))
                .availableColors(-1)
                .productName("Error Occurred")
                .availableSizes(List.of("HIDDEN", "..."))
                .build();
    }

}
