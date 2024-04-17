package com.micro.flow.config;

import com.micro.flow.repository.OuterwearRepository;
import com.micro.flow.repository.SocksRepository;
import com.micro.flow.service.OuterwearService;
import com.micro.flow.service.SocksService;
import com.micro.flow.service.impl.OuterwearServiceImpl;
import com.micro.flow.service.impl.SocksServiceImpl;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ClothesConfig {

    @Bean
    public SocksService socksService(SocksRepository socksRepository) {
        return new SocksServiceImpl(socksRepository);
    }

    @Bean
    public OuterwearService outerwearService(OuterwearRepository outerwearRepository) {
        return new OuterwearServiceImpl(outerwearRepository);
    }

}
