package com.micro.flow.repository;

import com.micro.flow.domain.Bag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BagRepositoryTests {
    private final BagRepository bagRepository;

    @Autowired
    public BagRepositoryTests(BagRepository bagRepository) {
        this.bagRepository = bagRepository;
    }

    @Test
    public void testFindByUsernameSuccess() {
        String username = "adminito25";
        Bag expected = bagRepository.save(new Bag(username));
        Optional<Bag> bag = bagRepository.findByUsername(username);

        assertTrue(bag.isPresent());
        assertEquals(expected, bag.get());
    }

    @Test
    public void testFindByUsernameFailure() {
        String username = "test";
        Optional<Bag> bag = bagRepository.findByUsername(username);

        assertTrue(bag.isEmpty());
    }
}
