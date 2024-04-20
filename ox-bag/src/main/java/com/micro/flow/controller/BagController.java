package com.micro.flow.controller;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.client.UserServiceFeignClient;
import com.micro.flow.mapper.BagMapper;
import com.micro.flow.service.BagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/my-bag")
public class BagController {
    private final BagService bagService;
    private final BagMapper bagMapper;
    private final UserServiceFeignClient userServiceFeignClient;
    private final ClothesServiceFeignClients clothesServiceFeignClients;

    @PostMapping("/{email}")
    public void create(@PathVariable("email") String email) {
        var bag = bagService.create(email);
        log.info("CREATED BAG: {} - for user - {}", bag, email);
    }

}
