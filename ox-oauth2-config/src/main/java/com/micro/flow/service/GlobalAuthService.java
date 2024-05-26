package com.micro.flow.service;

import org.springframework.stereotype.Service;

@Service
public class GlobalAuthService {
    public boolean isUserAuthenticated(String passedUsername, String authUsername) {
        return passedUsername.equals(authUsername);
    }
}
