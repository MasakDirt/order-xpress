package com.micro.flow.service;

import com.micro.flow.domain.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public interface AccountService {

    Account create(String username);

    Account getByUsername(String username);

    Account replenishAccount(String username, BigDecimal amount);

    Account debit(String username, BigDecimal totalPrice);

    Account buyClothes(String username, UUID bagId);

}
