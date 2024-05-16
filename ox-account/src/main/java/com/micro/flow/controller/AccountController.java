package com.micro.flow.controller;

import com.micro.flow.client.UserServiceFeignClient;
import com.micro.flow.domain.Account;
import com.micro.flow.dto.AccountResponse;
import com.micro.flow.dto.TransactionRequest;
import com.micro.flow.mapper.AccountMapper;
import com.micro.flow.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/{username}/account")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final UserServiceFeignClient userServiceFeignClient;

    @PostMapping
    public Long createAccount(@PathVariable("username") String username) {
        var account = accountService.create(username);
        log.debug("CREATE ACCOUNT: {}", username);

        return account.getId();
    }

    @GetMapping
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("username") String username) {
        var account = accountService.getByUsername(username);
        log.debug("Get account: {}, by username: {}", account, username);

        return getResponse(account, username);
    }

    @PostMapping("/replenish")
    public ResponseEntity<AccountResponse> replenishAccount(
            @PathVariable("username") String username,
            @RequestBody @Valid TransactionRequest transactionRequest) {
        var account = accountService.replenishAccount(username, transactionRequest.getAmount());
        log.debug("Replenish account: {}, by username: {}", account, username);

        return getResponse(account, username);
    }

    @PostMapping("/buy-clothes/{bag-id}")
    public ResponseEntity<AccountResponse> buyClothes(@PathVariable("username") String username,
                                                      @PathVariable("bag-id") UUID bagId) {
        var account = accountService.buyClothes(username, bagId);
        log.debug("Buy clothes account: {}, by username: {}", account, username);

        return getResponse(account, username);
    }

    private ResponseEntity<AccountResponse> getResponse(
            Account account, String username){
        return ResponseEntity.ok(accountMapper.getResponseFromDomain(account,
                userServiceFeignClient.getUserByUsername(username)));
    }

}
