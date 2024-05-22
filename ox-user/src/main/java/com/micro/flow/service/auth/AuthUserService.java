package com.micro.flow.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthUserService {
    public boolean isUserSame(String passedUsername, String authUsername) {
        return authUsername.equals(passedUsername);
    }
}
