package com.micro.flow;

import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.client.UserServiceFeignClient;
import com.micro.flow.config.AccountSecurityConfig;
import com.micro.flow.controller.AccountController;
import com.micro.flow.mapper.AccountMapper;
import com.micro.flow.repository.AccountRepository;
import com.micro.flow.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class OxAccountApplicationTests {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountController accountController;
    private final AccountSecurityConfig accountSecurityConfig;
    private final BagServiceFeignClient bagServiceFeignClient;
    private final UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public OxAccountApplicationTests(
            AccountService accountService, AccountRepository accountRepository,
            AccountMapper accountMapper, AccountController accountController,
            AccountSecurityConfig accountSecurityConfig,
            BagServiceFeignClient bagServiceFeignClient,
            UserServiceFeignClient userServiceFeignClient) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.accountController = accountController;
        this.accountSecurityConfig = accountSecurityConfig;
        this.bagServiceFeignClient = bagServiceFeignClient;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @Test
    public void tessInsertedValues() {
        Assertions.assertNotNull(accountService);
        Assertions.assertNotNull(accountRepository);
        Assertions.assertNotNull(accountMapper);
        Assertions.assertNotNull(accountController);
        Assertions.assertNotNull(accountSecurityConfig);
        Assertions.assertNotNull(userServiceFeignClient);
        Assertions.assertNotNull(bagServiceFeignClient);
    }
}
