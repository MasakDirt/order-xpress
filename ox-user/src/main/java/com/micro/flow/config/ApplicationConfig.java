package com.micro.flow.config;

import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.UserService;
import com.micro.flow.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserService userService(UserRepository userRepository,
                                   BagServiceFeignClient bagServiceFeignClient) {
        return new UserServiceImpl(userRepository, bagServiceFeignClient, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
