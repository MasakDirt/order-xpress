package com.micro.flow.controller;

import com.micro.flow.dto.UserResponse;
import com.micro.flow.mapper.UserMapper;
import com.micro.flow.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        var user = userService.readById(id);
        log.info("GET-USER-ID user === {}, timestamp == {}",
                user.getEmail(), LocalDateTime.now());

        return ResponseEntity
                .ok(userMapper.getUserResponseFromDomain(user));
    }

}
