package com.micro.flow.controller;

import com.micro.flow.dto.UserCreateRequest;
import com.micro.flow.dto.UserResponse;
import com.micro.flow.mapper.UserMapper;
import com.micro.flow.service.AccountService;
import com.micro.flow.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final AccountService accountService;
    private final UserMapper userMapper;

    @PostMapping("/signUp")
    public ResponseEntity<UserResponse> createUserWithBagAndAccount(
            @RequestBody @Valid UserCreateRequest createRequest) {
        var user = userService.createWithBag(userMapper.getUserFromCreateRequest(createRequest));
        accountService.create(user.getEmail());
        log.debug("POST-USER === created user == {}", user);

        return ok(userMapper.getUserResponseFromDomain(user));
    }

}
