package com.micro.flow.service;

import com.micro.flow.dto.LoginRequest;
import com.micro.flow.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    LoginResponse login(LoginRequest loginRequest);

}
