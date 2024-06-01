package com.micro.flow.repository;

import com.micro.flow.domain.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class AccountRepositoryTests {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountRepositoryTests(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Test
    public void testFindByUsernameSuccess() {
        String username = "test";
        Account expected = new Account(username);
        accountRepository.save(expected);
        Optional<Account> actual = accountRepository.findByUsername(username);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void testFindByUsernameFail() {
        String username = "notfound";
        Optional<Account> notFound = accountRepository.findByUsername(username);
        assertTrue(notFound.isEmpty());
    }
}
