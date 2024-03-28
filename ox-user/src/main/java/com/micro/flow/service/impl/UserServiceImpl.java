package com.micro.flow.service.impl;

import com.micro.flow.domain.User;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.micro.flow.domain.User.Role.USER;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(@NotNull User user) {
        return userRepository.save(user.setRoleAndReturn(USER));
    }

    @Override
    public User readByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
    }

    @Override
    public User readById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
    }

}
