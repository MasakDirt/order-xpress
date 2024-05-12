package com.micro.flow.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserRepositoryTests {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testFindByEmailSuccess() {
        String email = userRepository.findAll()
                .stream()
                .findAny()
                .get().getEmail();

        assertTrue(userRepository.findByEmail(email).isPresent());
    }

    @Test
    public void testFindByEmailFail() {
        String email = "invalid@mail.co";

        assertTrue(userRepository.findByEmail(email).isEmpty());
    }

}
