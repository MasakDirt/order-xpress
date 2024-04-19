package com.micro.flow.controller;

import com.micro.flow.dto.UserCreateRequest;
import com.micro.flow.dto.UserResponse;
import com.micro.flow.mapper.UserMapper;
import com.micro.flow.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) {
        var user = userService.readById(id);
        log.info("GET-USER-ID user === {}, timestamp == {}",
                user.getEmail(), LocalDateTime.now());

        return ok(userMapper.getUserResponseFromDomain(user));
    }

    @GetMapping("/e/{email}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("email") String email) {
        var user = userService.readByEmail(email);
        log.info("GET-USER-EMAIL user === {}, timestamp == {}",
                user.getEmail(), LocalDateTime.now());

        return ok(userMapper.getUserResponseFromDomain(user));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUserWithBag(@RequestBody @Valid UserCreateRequest createRequest) {
        var user = userService.createWithBag(userMapper.getUserFromCreateRequest(createRequest));
        log.info("POST-USER created user === {}", user);

        return ok(userMapper.getUserResponseFromDomain(user));
    }

}
