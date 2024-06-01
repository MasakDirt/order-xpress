package com.micro.flow.service;

import com.micro.flow.client.AccountServiceFeignClient;
import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.domain.Role;
import com.micro.flow.domain.User;
import com.micro.flow.repository.RoleRepository;
import com.micro.flow.repository.UserRepository;
import com.micro.flow.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private RoleRepository mockRoleRepository;
    @Mock
    private AccountServiceFeignClient mockAccountServiceFeignClient;
    @Mock
    private CredentialService mockCredentialService;

    @Autowired
    public UserServiceTests(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Test
    public void testCreateSuccess_Mock() {
        User expected = getAnyUserFromAll();
        UUID bagId = UUID.randomUUID();
        when(mockRoleRepository.getByName(anyString())).thenReturn(new Role());
        when(mockUserRepository.save(any(User.class))).thenReturn(expected);
        when(mockBagServiceFeignClient.createBag(anyString())).thenReturn(bagId);
        when(mockAccountServiceFeignClient.createAccount(anyString())).thenReturn(100L);
        when(mockUserRepository.saveAndFlush(any(User.class))).thenReturn(expected);
        mockUserService.create(expected, "1234");

        verify(mockRoleRepository, times(1)).getByName(anyString());
        verify(mockUserRepository, times(1)).save(any(User.class));
        verify(mockCredentialService, times(1)).createCredentialForUser(any(User.class), eq("1234"));
        verify(mockBagServiceFeignClient, times(1)).createBag(anyString());
        verify(mockAccountServiceFeignClient, times(1)).createAccount(anyString());
        verify(mockUserRepository, times(1)).saveAndFlush(any(User.class));
    }

    @Test
    public void testReadByUsernameSuccess_Spring() {
        User expected = getAnyUserFromAll();
        User actual = userService.readByUsername(expected.getUsername());

        assertEquals(expected, actual);
    }

    @Test
    public void testReadByUsernameNotFound_Spring() {
        assertThrows(EntityNotFoundException.class, () -> userService.readByUsername("invalid"));
    }

    private User getAnyUserFromAll() {
        return userRepository.findAll()
                .stream()
                .findAny()
                .orElseThrow();
    }

}
