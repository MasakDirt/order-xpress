package com.micro.flow.service.impl;

import com.micro.flow.client.AccountServiceFeignClient;
import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.domain.User;
import com.micro.flow.repository.RoleRepository;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.CredentialService;
import com.micro.flow.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountServiceFeignClient accountServiceFeignClient;
    private final BagServiceFeignClient bagServiceFeignClient;
    private final CredentialService credentialService;

    @Value("${spring.keycloak.auth.realm-id}")
    private String realmId;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           AccountServiceFeignClient accountServiceFeignClient,
                           BagServiceFeignClient bagServiceFeignClient,
                           CredentialService credentialService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountServiceFeignClient = accountServiceFeignClient;
        this.bagServiceFeignClient = bagServiceFeignClient;
        this.credentialService = credentialService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createWithBag(@NotNull User user, String password) {
        user.setImportantValues(realmId, Set.of(roleRepository.findByName("ox_user")));
        var created = userRepository.save(user);
        log.info("Created: {}", created);
        credentialService.createCredentialForUser(created, password);
        sendRequestForBagAndAccountCreation(created.getUsername());
        return userRepository.saveAndFlush(created);
    }

    private void sendRequestForBagAndAccountCreation(String username) {
        tryCreateBag(username);
        log.info("Bag successfully created!");
        tryCreateAccount(username);
        log.info("Account successfully created!");
    }

    private void tryCreateBag(String username) {
        UUID bagId = bagServiceFeignClient.createBag(username);
        log.info("Sent request for BAG creation!");
        checkBagForNull(bagId);
    }

    private void checkBagForNull(UUID bagId) {
        if (bagId == null) {
            log.error("Can't get bag id!");
            throw new IllegalArgumentException("Sorry our mistake. We are already working on it.");
        }
    }

    private void tryCreateAccount(String username) {
        Long id = accountServiceFeignClient.createAccount(username);
        log.info("Sent request for ACCOUNT creation!");
        checkAccountIdForNull(id);
    }

    private void checkAccountIdForNull(Long id) {
        if (id == null) {
            log.error("Can't get account id!");
            throw new IllegalArgumentException("Sorry our mistake. We are already working on it.");
        }
    }

    @Override
    public User readByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with username %s " +
                        "not found, please check if you provided valid username!", username)));
        log.info("Read user by username: {}", username);
        return user;
    }

}
