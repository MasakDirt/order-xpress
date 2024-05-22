package com.micro.flow.controller;

import com.micro.flow.dto.UserDtoForAccount;
import com.micro.flow.dto.UserResponse;
import com.micro.flow.mapper.UserMapper;
import com.micro.flow.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/u/{username}")
    @PreAuthorize("@authUserService.isUserSame(#username, authentication.name)")
    public ResponseEntity<UserResponse> getUserByUsername(
            @PathVariable("username") String username) {
        var user = userService.readByUsername(username);
        log.debug("GET-USER-USERNAME === user == {}, timestamp == {}",
                username, LocalDateTime.now());

        return ok(userMapper.getUserResponseFromDomain(user));
    }

    @GetMapping("/for-account/{username}")
    @PreAuthorize("@authUserService.isUserSame(#username, authentication.name)")
    public ResponseEntity<UserDtoForAccount> getUserDtoForAccount(
            @PathVariable("username") String username) {
        var userDtoForAccount = userMapper.getUserDtoForAccountFromDomain(
                userService.readByUsername(username));
        log.debug("GET-USER_FOR_ACCOUNT === user == {}", username);

        return ok(userDtoForAccount);
    }

}
