package com.micro.flow;

import com.micro.flow.config.ClothesSecurityConfig;
import com.micro.flow.controller.ClothesController;
import com.micro.flow.mapper.ClothesMapper;
import com.micro.flow.repository.ClothesRepository;
import com.micro.flow.service.ClothesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class OxClothesApplicationTests {
    private final ClothesSecurityConfig clothesSecurityConfig;
    private final ClothesController clothesController;
    private final ClothesMapper clothesMapper;
    private final ClothesRepository clothesRepository;
    private final ClothesService clothesService;

    @Autowired
    public OxClothesApplicationTests(ClothesSecurityConfig clothesSecurityConfig,
                                     ClothesController controller, ClothesMapper clothesMapper,
                                     ClothesRepository clothesRepository,
                                     ClothesService clothesService) {
        this.clothesSecurityConfig = clothesSecurityConfig;
        this.clothesController = controller;
        this.clothesMapper = clothesMapper;
        this.clothesRepository = clothesRepository;
        this.clothesService = clothesService;
    }

    @Test
    public void testInjectedValues() {
        assertNotNull(clothesSecurityConfig);
        assertNotNull(clothesController);
        assertNotNull(clothesMapper);
        assertNotNull(clothesRepository);
        assertNotNull(clothesService);
    }
}
