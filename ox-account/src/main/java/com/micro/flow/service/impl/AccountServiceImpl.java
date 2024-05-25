package com.micro.flow.service.impl;

import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.domain.Account;
import com.micro.flow.repository.AccountRepository;
import com.micro.flow.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.function.Supplier;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BagServiceFeignClient bagServiceFeignClient;

    @Override
    public Account create(String username) {
        if (isAccountNew(username)) {
            return createNewAccount(username);
        }
        log.info("Account for username {} already exist", username);
        return getByUsername(username);
    }

    private boolean isAccountNew(String username) {
        return accountRepository.findByUsername(username).isEmpty();
    }

    private Account createNewAccount(String username) {
        var account = accountRepository.save(new Account(username));
        log.info("Created new account for user {}", username);
        return account;
    }

    @Override
    public Account getByUsername(String username) {
        var account = accountRepository.findByUsername(username).orElseThrow(
                accountNotFound(username));
        log.info("Account found by name! - {}", username);
        return account;
    }

    private Supplier<EntityNotFoundException> accountNotFound(String username) {
        return () -> {
            log.error("Account not found by username! - {}", username);
            return new EntityNotFoundException("Account by username not found " + username +
                    ". It's our mistake! We are already working on it!");
        };
    }

    @Override
    public Account replenishAccount(String username, BigDecimal replenishFor) {
        var account = getByUsername(username);
        account.replenish(replenishFor);
        log.info("Account was replenished for {}! For user - {}", replenishFor, username);
        return accountRepository.save(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account buyClothes(String username) {
        var totalPrice = getBagTotalPriceByUsername(username);
        var account = debit(username, totalPrice);
        removeClothesFromBagAfterBuying(username);
        log.info("USER {} bought clothes for {}", username, totalPrice);
        return account;
    }

    private BigDecimal getBagTotalPriceByUsername(String username) {
        log.info("GET bag username {}", username);
        var totalPrice = bagServiceFeignClient.getBagTotalPrice(username);
        log.info("Get total price from bag {}", totalPrice);
        return totalPrice;
    }

    private Account debit(String username, BigDecimal totalPrice) {
        var account = getByUsername(username);
        account.debit(totalPrice);
        log.info("Debit account for {}! For user - {}", totalPrice, username);
        return accountRepository.save(account);
    }

    private void removeClothesFromBagAfterBuying(String username) {
        bagServiceFeignClient.resetClothes(username);
        log.info("Remove clothes from bag {}", username);
    }
}
