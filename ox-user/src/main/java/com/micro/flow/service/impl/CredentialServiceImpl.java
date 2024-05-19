package com.micro.flow.service.impl;

import com.micro.flow.component.PasswordHashingUtil;
import com.micro.flow.domain.Credential;
import com.micro.flow.domain.User;
import com.micro.flow.repository.CredentialRepository;
import com.micro.flow.service.CredentialService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService {
    private final CredentialRepository credentialRepository;
    private final PasswordHashingUtil passwordHashingUtil;

    @Override
    public void createCredentialForUser(User user, String password) {
        checkPasswordForNull(password);

        String secretData = passwordHashingUtil.getSecretDataForPasswordCredential(password);
        String credentialData = passwordHashingUtil.getCredentialDataForPasswordCredential();

        Credential credential = new Credential(user.getId(), secretData, credentialData);
        credentialRepository.save(credential);

        log.info("Credential for {} user, created: {}", user.getUsername(), credential);
    }

    private void checkPasswordForNull(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password must be included!");
        }
    }
}
