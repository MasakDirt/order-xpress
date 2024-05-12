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
        log.debug("GET-USER-ID user === {}, timestamp == {}",
                user.getEmail(), LocalDateTime.now());

        return ok(userMapper.getUserResponseFromDomain(user));
    }

    @GetMapping("/e/{email}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("email") String email) {
        var user = userService.readByEmail(email);
        log.debug("GET-USER-EMAIL === user == {}, timestamp == {}",
                user.getEmail(), LocalDateTime.now());

        return ok(userMapper.getUserResponseFromDomain(user));
    }

}
