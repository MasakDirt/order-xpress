package com.micro.flow.config;

import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.UserService;
import com.micro.flow.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

}
