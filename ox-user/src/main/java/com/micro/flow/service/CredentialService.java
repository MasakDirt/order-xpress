package com.micro.flow.service;

import com.micro.flow.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface CredentialService {

    void createCredentialForUser(User user, String password);

}
