package com.micro.flow.controller;

import com.micro.flow.client.UserServiceFeignClient;
import com.micro.flow.domain.Account;
import com.micro.flow.dto.AccountResponse;
import com.micro.flow.dto.TransactionRequest;
import com.micro.flow.dto.UserDtoForAccount;
import com.micro.flow.mapper.AccountMapper;
import com.micro.flow.repository.AccountRepository;
import com.micro.flow.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.micro.flow.AccountTestingUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class AccountControllerTests {
    private static final String BASIC_URL = "/api/v1/user/{username}/account";

    private final MockMvc mockMvc;
    private final AccountMapper accountMapper;
    private final UserServiceFeignClient userServiceFeignClient;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService mockAccountService;
    @Mock
    private UserServiceFeignClient mockUserServiceFeignClient;
    @Mock
    private AccountMapper mockAccountMapper;


    @Autowired
    public AccountControllerTests(MockMvc mockMvc, AccountMapper accountMapper,
                                  UserServiceFeignClient userServiceFeignClient,
                                  AccountService accountService,
                                  AccountRepository accountRepository) {
        this.mockMvc = mockMvc;
        this.accountMapper = accountMapper;
        this.userServiceFeignClient = userServiceFeignClient;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Test
    public void testCreateAccountSuccess() throws Exception {
        String username = "stonisloff24";

        performCreate(username)
                .andExpect(status().isCreated())
                .andExpect(result -> assertEquals(
                        asJsonString(accountRepository.findAll().size()),
                        result.getResponse().getContentAsString()));
    }

    private ResultActions performCreate(String username) throws Exception {
        return mockMvc.perform(post(BASIC_URL, username));
    }

    @Test
    @WithMockUser(username = "davidos")
    public void testGetAccountSuccess() throws Exception {
        String username = "davidos";
        AccountResponse expected = accountMapper.getResponseFromDomain(accountService.getByUsername(username),
                userServiceFeignClient.getUserByUsername(username));

        performGetAccount(username)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected).substring(0, 20),
                        result.getResponse().getContentAsString().substring(0, 20)));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testGetAccountNotFound() throws Exception {
        String username = "notfound";

        performGetAccount(username)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"NOT_FOUND\",\"message\":" +
                                "\"Account by username not found notfound. " +
                                "It's our mistake! We are already working on it!\"")));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testGetAccountForbidden() throws Exception {
        String username = "adminito25";

        performGetAccount(username)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performGetAccount(String username) throws Exception {
        return mockMvc.perform(get(BASIC_URL, username));
    }

    @Test
    @WithMockUser(username = "markiz23")
    public void testReplenishSuccess() throws Exception {
        String username = "markiz23";
        BigDecimal amount = BigDecimal.valueOf(100);
        Account account = accountService.getByUsername(username);
        account.setBalance(account.getBalance().add(amount));

        performReplenishAccount(username, amount)
                .andExpect(status().isOk());

        assertEquals(account.getBalance(), accountService.getByUsername(username).getBalance());
    }

    @ParameterizedTest
    @WithMockUser(username = "adminito25")
    @MethodSource("getAmountForAccountReplenish")
    public void testReplenishWithZeroAndSmaller(BigDecimal amount) throws Exception {
        String username = "adminito25";

        performReplenishAccount(username, amount)
                .andExpect(status().isBadRequest())
                .andExpectAll(result -> {
                    if (amount == null || amount.equals(BigDecimal.ZERO)) {
                        assertTrue(result.getResponse().getContentAsString()
                                .contains("\"status\":\"BAD_REQUEST\",\"message\"" +
                                        ":\"The replenish amount must be greater than zero!\""));
                    } else {
                        assertTrue(result.getResponse().getContentAsString()
                                .contains("\"status\":\"BAD_REQUEST\",\"message\"" +
                                        ":\"Transaction amount must be greater than zero!\""));
                    }
                });
    }

    private static Stream<BigDecimal> getAmountForAccountReplenish() {
        return Stream.of(BigDecimal.ZERO, BigDecimal.valueOf(-0.1),
                BigDecimal.valueOf(-1), null);
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testReplenishNotFound() throws Exception {
        String username = "notfound";

        performReplenishAccount(username, BigDecimal.valueOf(1))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"NOT_FOUND\",\"message\":" +
                                "\"Account by username not found notfound. " +
                                "It's our mistake! We are already working on it!\"")));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testReplenishForbidden() throws Exception {
        String username = "markiz23";

        performReplenishAccount(username, BigDecimal.valueOf(1))
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performReplenishAccount(
            String username, BigDecimal amount) throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest(amount);

        return mockMvc.perform(put(BASIC_URL + "/replenish", username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transactionRequest)));
    }

    @Test
    @WithMockUser(username = "adminito25")
    public void testBuyClothesSuccess() {
        String username = "adminito25";
        Account account = new Account(username);
        BigDecimal balance = BigDecimal.valueOf(100);
        account.setBalance(balance);

        BigDecimal totalPrice = BigDecimal.valueOf(20);
        when(mockAccountService.buyClothes(username)).thenReturn(account);
        when(mockUserServiceFeignClient.getUserByUsername(username))
                .thenReturn(new UserDtoForAccount());
        when(mockAccountMapper.getResponseFromDomain(account, new UserDtoForAccount()))
                .thenReturn(new AccountResponse(
                        12L, balance.subtract(totalPrice), new UserDtoForAccount()));
        ResponseEntity<AccountResponse> actual = accountController.buyClothes(username);

        verify(mockAccountService, times(1)).buyClothes(username);
        verify(mockUserServiceFeignClient, times(1)).getUserByUsername(username);

        assertEquals(account.getBalance().subtract(totalPrice), actual.getBody().getBalance());
    }

    @Test
    @WithMockUser(username = "davidos")
    public void testBuyClothesWithInvalidBag() throws Exception {
        String username = "davidos";

        performBuyClothes(username)
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"INTERNAL_SERVER_ERROR\"," +
                                "\"message\":\"[401] during [POST] " +
                                "to [http://ox-bag/api/v1/my-bag/davidos/reset-bag]")));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testBuyClothesNotFound() throws Exception {
        String username = "notfound";

        performBuyClothes(username)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"NOT_FOUND\",\"message\":" +
                                "\"Account by username not found notfound. " +
                                "It's our mistake! We are already working on it!\"")));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testBuyClothesForbidden() throws Exception {
        String username = "davidos";

        performBuyClothes(username)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performBuyClothes(String username) throws Exception {
        return mockMvc.perform(post(BASIC_URL + "/buy-clothes", username));
    }

    private ResultMatcher getForbiddenStatus() {
        return status().isForbidden();
    }

    private ResultMatcher getForbiddenResult() {
        return result -> assertTrue(result.getResponse().getContentAsString()
                .contains("\"status\":\"FORBIDDEN\",\"message\":\"Access Denied\""));
    }
}
