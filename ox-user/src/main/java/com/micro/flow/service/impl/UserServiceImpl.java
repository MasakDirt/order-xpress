package com.micro.flow.service.impl;

import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.domain.User;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.micro.flow.domain.User.Role.USER;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BagServiceFeignClient bagServiceFeignClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createWithBag(@NotNull User user) {
        var created = userRepository.save(user.setRoleAndEncodePasswordAndGet(USER, passwordEncoder));
        bagServiceFeignClient.createBag(created.getEmail());
        return created;
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
