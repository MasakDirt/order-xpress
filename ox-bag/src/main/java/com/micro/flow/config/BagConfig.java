package com.micro.flow.config;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.client.UserServiceFeignClient;
import com.micro.flow.mapper.BagMapper;
import com.micro.flow.mapper.impl.BagMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BagConfig {

    @Bean
    public BagMapper bagMapper(ClothesServiceFeignClients clothesServiceFeignClients,
                               UserServiceFeignClient userServiceFeignClient) {
        return new BagMapperImpl(userServiceFeignClient, clothesServiceFeignClients);
    }

}
