package com.micro.flow;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.client.UserServiceFeignClient;
import com.micro.flow.config.BagConfig;
import com.micro.flow.config.BagSecurityConfig;
import com.micro.flow.controller.BagController;
import com.micro.flow.mapper.BagMapper;
import com.micro.flow.repository.BagRepository;
import com.micro.flow.service.BagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class OxBagApplicationTests {
    private final BagService bagService;
    private final BagRepository bagRepository;
    private final BagMapper bagMapper;
    private final BagController bagController;
    private final BagConfig bagConfig;
    private final BagSecurityConfig bagSecurityConfig;
    private final ClothesServiceFeignClients clothesServiceFeignClients;
    private final UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public OxBagApplicationTests(BagService bagService, BagRepository bagRepository,
                                 BagMapper bagMapper, BagController bagController,
                                 BagConfig bagConfig, BagSecurityConfig bagSecurityConfig,
                                 ClothesServiceFeignClients clothesServiceFeignClients,
                                 UserServiceFeignClient userServiceFeignClient) {
        this.bagService = bagService;
        this.bagRepository = bagRepository;
        this.bagMapper = bagMapper;
        this.bagController = bagController;
        this.bagConfig = bagConfig;
        this.bagSecurityConfig = bagSecurityConfig;
        this.clothesServiceFeignClients = clothesServiceFeignClients;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @Test
    public void testInjectedComponents() {
        Assertions.assertNotNull(bagService);
        Assertions.assertNotNull(bagRepository);
        Assertions.assertNotNull(bagMapper);
        Assertions.assertNotNull(bagController);
        Assertions.assertNotNull(bagConfig);
        Assertions.assertNotNull(bagSecurityConfig);
        Assertions.assertNotNull(clothesServiceFeignClients);
        Assertions.assertNotNull(userServiceFeignClient);
    }
}
