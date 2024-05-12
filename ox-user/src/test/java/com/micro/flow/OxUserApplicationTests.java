package com.micro.flow;

import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.config.SecurityConfig;
import com.micro.flow.controller.AuthController;
import com.micro.flow.controller.UserController;
import com.micro.flow.mapper.UserMapper;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class OxUserApplicationTests {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserController userController;
    private final AuthController authController;
    private final SecurityConfig securityConfig;
    private final BagServiceFeignClient bagServiceFeignClient;

    @Autowired
    public OxUserApplicationTests(UserService userService, UserRepository userRepository,
                                  UserMapper userMapper, UserController userController,
                                  AuthController authController, SecurityConfig securityConfig,
                                  BagServiceFeignClient bagServiceFeignClient) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userController = userController;
        this.authController = authController;
        this.securityConfig = securityConfig;
        this.bagServiceFeignClient = bagServiceFeignClient;
    }

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(userMapper);
        Assertions.assertNotNull(userController);
        Assertions.assertNotNull(authController);
        Assertions.assertNotNull(securityConfig);
        Assertions.assertNotNull(bagServiceFeignClient);
    }

}
