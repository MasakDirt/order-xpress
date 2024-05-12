package com.micro.flow.service.impl;

import com.micro.flow.domain.Account;
import com.micro.flow.repository.AccountRepository;
import com.micro.flow.service.AccountService;
import com.micro.flow.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Supplier;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    @Override
    public Account create(String userEmail) {
        var account = new Account();
        account.setUser(userService.readByEmail(userEmail));

        return accountRepository.save(account);
    }

    @Override
    public Account getByUserEmail(String userEmail) {
        var account = accountRepository.findByUserEmail(userEmail).orElseThrow(
                accountNotFound(userEmail));
        log.info("Account found by email! - {}", userEmail);
        return account;
    }

    private Supplier<EntityNotFoundException> accountNotFound(String userEmail) {
        return () -> {
            log.error("Account not found by email! - {}", userEmail);
            return new EntityNotFoundException("Account by user email not found " + userEmail +
                    ". It's our mistake! We are already working on it!");
        };
    }

    @Override
    public Account replenishAccount(String userEmail, BigDecimal replenishFor) {
        var account = getByUserEmail(userEmail);
        account.replenish(replenishFor);
        log.info("Account was replenished for {}! For user - {}", replenishFor, userEmail);
        return accountRepository.save(account);
    }

    @Override
    public Account debit(String userEmail, BigDecimal totalPrice) {
        var account = getByUserEmail(userEmail);
        account.debit(totalPrice);
        log.info("Debit account for {}! For user - {}", totalPrice, userEmail);
        return accountRepository.save(account);
    }

}
