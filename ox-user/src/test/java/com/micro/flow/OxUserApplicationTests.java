package com.micro.flow;

import com.micro.flow.client.AccountServiceFeignClient;
import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.client.KeycloakLoginFeignClient;
import com.micro.flow.component.PasswordHashingUtil;
import com.micro.flow.config.UserSecurityConfig;
import com.micro.flow.controller.AuthController;
import com.micro.flow.controller.UserController;
import com.micro.flow.mapper.UserMapper;
import com.micro.flow.repository.CredentialRepository;
import com.micro.flow.repository.RoleRepository;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.CredentialService;
import com.micro.flow.service.LoginService;
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
    private final LoginService loginService;
    private final CredentialService credentialService;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserController userController;
    private final AuthController authController;
    private final UserSecurityConfig userSecurityConfig;
    private final PasswordHashingUtil passwordHashingUtil;
    private final KeycloakLoginFeignClient keycloakLoginFeignClient;
    private final BagServiceFeignClient bagServiceFeignClient;
    private final AccountServiceFeignClient accountServiceFeignClient;

    @Autowired
    public OxUserApplicationTests(UserService userService, LoginService loginService,
                                  CredentialService credentialService,
                                  RoleRepository roleRepository,
                                  CredentialRepository credentialRepository,
                                  UserRepository userRepository, UserMapper userMapper,
                                  UserController userController, AuthController authController,
                                  UserSecurityConfig userSecurityConfig,
                                  PasswordHashingUtil passwordHashingUtil,
                                  KeycloakLoginFeignClient keycloakLoginFeignClient,
                                  BagServiceFeignClient bagServiceFeignClient,
                                  AccountServiceFeignClient accountServiceFeignClient) {
        this.userService = userService;
        this.loginService = loginService;
        this.credentialService = credentialService;
        this.roleRepository = roleRepository;
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userController = userController;
        this.authController = authController;
        this.userSecurityConfig = userSecurityConfig;
        this.passwordHashingUtil = passwordHashingUtil;
        this.keycloakLoginFeignClient = keycloakLoginFeignClient;
        this.bagServiceFeignClient = bagServiceFeignClient;
        this.accountServiceFeignClient = accountServiceFeignClient;
    }

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(loginService);
        Assertions.assertNotNull(credentialService);
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(roleRepository);
        Assertions.assertNotNull(credentialRepository);
        Assertions.assertNotNull(userMapper);
        Assertions.assertNotNull(userController);
        Assertions.assertNotNull(authController);
        Assertions.assertNotNull(userSecurityConfig);
        Assertions.assertNotNull(passwordHashingUtil);
        Assertions.assertNotNull(keycloakLoginFeignClient);
        Assertions.assertNotNull(bagServiceFeignClient);
        Assertions.assertNotNull(accountServiceFeignClient);
    }

}
