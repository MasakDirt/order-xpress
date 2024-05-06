package com.micro.flow.config;

import com.micro.flow.repository.ClothesRepository;
import com.micro.flow.service.ClothesService;
import com.micro.flow.service.impl.ClothesServiceImpl;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ClothesConfig {

    @Bean
    public ClothesService clothesService(ClothesRepository clothesRepository) {
        return new ClothesServiceImpl(clothesRepository);
    }

}
