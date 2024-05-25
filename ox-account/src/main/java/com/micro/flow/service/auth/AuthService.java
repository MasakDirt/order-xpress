package com.micro.flow.service.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public boolean isUserAuthenticated(String passedUsername, String authUsername) {
        return passedUsername.equals(authUsername);
    }
}
