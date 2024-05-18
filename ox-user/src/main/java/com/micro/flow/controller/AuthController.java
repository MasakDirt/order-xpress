package com.micro.flow.controller;

import com.micro.flow.dto.LoginRequest;
import com.micro.flow.dto.LoginResponse;
import com.micro.flow.dto.UserCreateRequest;
import com.micro.flow.dto.UserResponse;
import com.micro.flow.mapper.UserMapper;
import com.micro.flow.service.LoginService;
import com.micro.flow.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        var loginResponse = loginService.login(loginRequest);
        log.debug("POST-LOGIN === {}, timestamp - {}",
                loginRequest.getUsername(), LocalDateTime.now());

        return ok(loginResponse);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserResponse> createUserWithBagAndAccount(
            @RequestBody @Valid UserCreateRequest createRequest) {
        var user = userService.createWithBag(userMapper.getUserFromCreateRequest(createRequest));
        log.debug("POST-USER === created user == {}", user);

        return status(HttpStatus.CREATED)
                .body(userMapper.getUserResponseFromDomain(user));
    }

}
