package com.micro.flow.service.impl;

import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.domain.User;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.micro.flow.domain.User.Role.USER;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BagServiceFeignClient bagServiceFeignClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createWithBag(@NotNull User user) {
        var created = saveUserWithRole(user);
        log.info("Created user: {}", created);
        var bagId = sendRequestForBagCreation(created.getEmail());
        created.setBagId(bagId);
        log.info("Bag successfully created!");
        return userRepository.saveAndFlush(created);
    }

    private User saveUserWithRole(@NotNull User user) {
        return userRepository.save(user.setRoleAndEncodePassword(USER, passwordEncoder));
    }

    private UUID sendRequestForBagCreation(String email) {
        UUID bagId = bagServiceFeignClient.createBag(email);
        log.info("Sent request for BAG creation!");
        checkBagForNull(bagId);
        return bagId;
    }

    private void checkBagForNull(UUID bagId) {
        if (bagId == null) {
            log.error("Can't get bag id!");
            throw new IllegalArgumentException("Sorry our mistake. We are already working on it.");
        }
    }

    @Override
    public User readByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with email %s not found," +
                        " please check if you provided valid e-mail!", email)));
        log.info("Read user by email: {}", email);
        return user;
    }

    @Override
    public User readById(Long id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
        log.info("Read user by id: {}", id);
        return user;
    }

}
