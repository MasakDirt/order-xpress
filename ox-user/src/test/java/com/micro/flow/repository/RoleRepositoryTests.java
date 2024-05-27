package com.micro.flow.repository;

import com.micro.flow.domain.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class RoleRepositoryTests {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryTests(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    public void testGetByName_Success() {
        String name = "ox_user";
        Role role = roleRepository.getByName(name);

        Assertions.assertNotNull(role);
        Assertions.assertEquals(role.getName(), name);
    }

    @Test
    public void testGetByName_Invalid() {
        String name = "invalid_name";
        Role role = roleRepository.getByName(name);

        Assertions.assertNull(role);
    }

}
