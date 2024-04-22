package com.micro.flow.config;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.mapper.BagMapper;
import com.micro.flow.mapper.impl.BagMapperImpl;
import com.micro.flow.repository.BagRepository;
import com.micro.flow.service.BagService;
import com.micro.flow.service.iml.BagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BagConfig {

    @Bean
    public BagService bagService(BagRepository bagRepository,
                                 ClothesServiceFeignClients clothesServiceFeignClients) {
        return new BagServiceImpl(bagRepository, clothesServiceFeignClients);
    }

    @Bean
    public BagMapper bagMapper() {
        return new BagMapperImpl();
    }

}
