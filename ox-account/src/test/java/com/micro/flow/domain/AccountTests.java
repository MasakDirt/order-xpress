package com.micro.flow.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.micro.flow.AccountTestingUtil.getViolation;
import static com.micro.flow.AccountTestingUtil.testInvalidField;

@SpringBootTest
@ActiveProfiles("test")
public class AccountTests {
    private Account account;

    @BeforeEach
    public void initAccount() {
        account = new Account();
        account.setUsername("username");
        account.setBalance(BigDecimal.ONE);
    }

    @Test
    public void testValidAccount() {
        Assertions.assertEquals(0, getViolation(account).size());
    }

    @ParameterizedTest
    @MethodSource("getInvalidUsernames")
    public void testInvalidUsernameValue(String username) {
        account.setUsername(username);
        testInvalidField(account, username,
                "Username must be lowercase and can contain only letters, " +
                        "numbers, underscores, and dots");
    }

    private static Stream<String> getInvalidUsernames() {
        return Stream.of("userName", "usErname", "12Username", "", null);
    }

    @ParameterizedTest
    @MethodSource("getInvalidBalance")
    public void testInvalidBalance(BigDecimal balance) {
        account.setBalance(balance);

        testInvalidField(account, balance,
                "Your balance can't be lower than zero!");

    }

    private static Stream<BigDecimal> getInvalidBalance() {
        return Stream.of(BigDecimal.valueOf(-0.1), BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-10), null);
    }
}
