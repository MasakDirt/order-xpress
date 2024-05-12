package com.micro.flow.service;

import com.micro.flow.domain.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface AccountService {

    Account create(String userEmail);

    Account getByUserEmail(String userEmail);

    Account replenishAccount(String userEmail, BigDecimal amount);

    Account debit(String userEmail, BigDecimal totalPrice);

}
