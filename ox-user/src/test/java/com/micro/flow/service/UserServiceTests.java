package com.micro.flow.service;

import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.domain.User;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserServiceTests {
    private final UserService userService;
    private final UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl mockUserService;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private BagServiceFeignClient mockBagServiceFeignClient;
    @Mock
    private PasswordEncoder mockPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceTests(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Test
    public void testCreateWithBagSuccess_Mock() {
        User expected = getAnyUserFromAll();
        when(mockUserRepository.save(any(User.class))).thenReturn(expected);
        when(mockPasswordEncoder.encode(anyString())).thenReturn("ldsakjfsfbddjskoal");
        mockUserService.createWithBag(expected);

        verify(mockBagServiceFeignClient, times(1)).createBag(anyString());
        verify(mockUserRepository, times(1)).save(any(User.class));
        verify(mockPasswordEncoder, times(1)).encode(anyString());
    }

    @Test
    public void testReadByEmailSuccess_Spring() {
        User expected = getAnyUserFromAll();
        User actual = userService.readByEmail(expected.getEmail());

        assertEquals(expected, actual);
    }

    @Test
    public void testReadByEmailNotFound_Spring() {
        assertThrows(EntityNotFoundException.class, () -> userService.readByEmail("invalid"));
    }

    @Test
    public void testReadByIdSuccess_Spring() {
        User expected = getAnyUserFromAll();
        User actual = userService.readById(expected.getId());

        assertEquals(expected, actual);
    }

    private User getAnyUserFromAll() {
        return userRepository.findAll()
                .stream()
                .findAny()
                .orElseThrow();
    }

    @Test
    public void testReadByIdNotFound_Spring() {
        assertThrows(EntityNotFoundException.class, () -> userService.readById(0L));
    }

}
