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

import static com.micro.flow.domain.User.Role.USER;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BagServiceFeignClient bagServiceFeignClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public User createWithBag(@NotNull User user) {
        var created = userRepository.save(user.setRoleAndEncodePassword(USER, passwordEncoder));
        log.info("Created user: {}", created);
        bagServiceFeignClient.createBag(created.getEmail());
        log.info("Sent request for BAG creation!");
        return created;
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
