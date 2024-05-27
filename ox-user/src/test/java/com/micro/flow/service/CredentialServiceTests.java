package com.micro.flow.service;

import com.micro.flow.component.PasswordHashingUtil;
import com.micro.flow.domain.Credential;
import com.micro.flow.domain.User;
import com.micro.flow.repository.CredentialRepository;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.impl.CredentialServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CredentialServiceTests {
    private final CredentialRepository credentialRepository;
    private final CredentialService credentialService;
    private final UserRepository userRepository;

    @InjectMocks
    private CredentialServiceImpl mockCredentialService;
    @Mock
    private CredentialRepository mockCredentialRepository;
    @Mock
    private PasswordHashingUtil mockPasswordHashingUtil;

    @Autowired
    public CredentialServiceTests(CredentialRepository credentialRepository,
                                  CredentialService credentialService, UserRepository userRepository) {
        this.credentialRepository = credentialRepository;
        this.credentialService = credentialService;
        this.userRepository = userRepository;
    }

    @Test
    public void testCreateCredentialSuccess() {
        User user = createUser();
        String password = "new pass";
        int credentialsBefore = credentialRepository.findAll().size();
        credentialService.createCredentialForUser(user, password);
        int credentialsAfter = credentialRepository.findAll().size();

        Assertions.assertTrue(credentialsBefore < credentialsAfter);
        Credential findByUser = credentialRepository.findAll()
                .stream()
                .filter(credential -> credential.getUserId().equals(user.getId()))
                .findAny().orElseThrow();

        Assertions.assertNotNull(findByUser);
        Assertions.assertEquals(user.getId(), findByUser.getUserId());
    }

    private User createUser() {
        String email = "test@test.com";
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setEmailConstraint(email);
        user.setUsername("test");
        user.setEmailVerified(true);
        user.setRealmId("tested realm");
        return userRepository.save(user);
    }

    @Test
    public void testCreateCredentialException() {
        User user = new User();
        String password = "  ";

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                credentialService.createCredentialForUser(user, password));
    }

    @Test
    public void testCreateCredentialByMock() {
        User user = new User();
        String password = "new pass";
        Credential credential = new Credential();

        when(mockPasswordHashingUtil.getSecretData(password)).thenReturn("some secret data");
        when(mockPasswordHashingUtil.getCredentialData()).thenReturn("some credential data");
        when(mockCredentialRepository.save(any(Credential.class))).thenReturn(credential);
        mockCredentialService.createCredentialForUser(user, password);

        verify(mockPasswordHashingUtil, times(1)).getSecretData(password);
        verify(mockPasswordHashingUtil, times(1)).getCredentialData();
        verify(mockCredentialRepository, times(1)).save(any(Credential.class));
    }
}
