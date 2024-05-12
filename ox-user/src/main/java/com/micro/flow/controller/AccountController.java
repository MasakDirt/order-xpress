package com.micro.flow.controller;

import com.micro.flow.dto.AccountResponse;
import com.micro.flow.dto.TransactionRequest;
import com.micro.flow.mapper.AccountMapper;
import com.micro.flow.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/{email}/account")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("email") String userEmail) {
        var account = accountService.getByUserEmail(userEmail);
        log.debug("Get account: {}, by user email: {}", account, userEmail);

        return ResponseEntity.ok(accountMapper.getResponseFromDomain(account));
    }

    @PostMapping("/replenish")
    public ResponseEntity<AccountResponse> replenishAccount(
            @PathVariable("email") String userEmail,
            @RequestBody @Valid TransactionRequest transactionRequest) {
        var account = accountService.replenishAccount(userEmail, transactionRequest.getAmount());
        log.debug("Replenish account: {}, by user email: {}", account, userEmail);

        return ResponseEntity.ok(accountMapper.getResponseFromDomain(account));
    }

    @PostMapping("/debit")
    public ResponseEntity<AccountResponse> debit(
            @PathVariable("email") String userEmail,
            @RequestBody @Valid TransactionRequest transactionRequest) {
        var account = accountService.debit(userEmail, transactionRequest.getAmount());
        log.debug("Debit account: {}, by user email: {}", account, userEmail);

        return ResponseEntity.ok(accountMapper.getResponseFromDomain(account));
    }

}
