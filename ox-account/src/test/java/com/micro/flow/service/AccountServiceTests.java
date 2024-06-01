package com.micro.flow.service;

import com.micro.flow.client.BagServiceFeignClient;
import com.micro.flow.domain.Account;
import com.micro.flow.exception.BalanceException;
import com.micro.flow.exception.ReplenishException;
import com.micro.flow.repository.AccountRepository;
import com.micro.flow.service.impl.AccountServiceImpl;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class AccountServiceTests {
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl mockAccountService;
    @Mock
    private BagServiceFeignClient bagServiceFeignClient;
    @Mock
    private AccountRepository mockAccountRepository;

    @Autowired
    public AccountServiceTests(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Test
    public void testCreateSuccess() {
        String username = "tested";
        int sizeBeforeCreating = accountRepository.findAll().size();
        Account expected = new Account(username);
        Account actual = accountService.create(username);
        expected.setId(actual.getId());
        int sizeAfterCreating = accountRepository.findAll().size();

        assertTrue(sizeBeforeCreating < sizeAfterCreating);
        assertEquals(expected, actual);
    }

    @Test
    public void testCreateAlreadyExist() {
        String username = "testedfail";
        Account expected = accountRepository.save(new Account(username));
        int sizeBeforeCreating = accountRepository.findAll().size();
        Account actual = accountService.create(username);
        int sizeAfterCreating = accountRepository.findAll().size();

        assertEquals(sizeBeforeCreating, sizeAfterCreating);
        assertEquals(expected, actual);

    }

    @Test
    public void testCreateFailWithNull() {
        String username = null;
        assertThrows(ConstraintViolationException.class, () -> accountService.create(username));
    }

    @Test
    public void testGetByUsernameSuccess() {
        String username = "bibus";
        Account expected = accountRepository.findByUsername(username).orElseThrow();
        Account actual = accountService.getByUsername(username);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByUsernameFailure() {
        String username = "notfound";
        assertThrows(EntityNotFoundException.class, () -> accountService.getByUsername(username));
    }

    @Test
    public void testReplenishAccountSuccess() {
        String username = "loperlo";
        Account accountBeforeReplenish = accountService.getByUsername(username);
        BigDecimal balanceBeforeReplenish = accountBeforeReplenish.getBalance();
        BigDecimal replenishAmount = BigDecimal.valueOf(100);
        accountService.replenishAccount(username, replenishAmount);
        Account accountAfterReplenish = accountService.getByUsername(username);
        BigDecimal balanceAfterReplenish = accountAfterReplenish.getBalance();

        assertTrue(balanceBeforeReplenish.intValue() < balanceAfterReplenish.intValue());
        assertEquals(balanceBeforeReplenish.add(replenishAmount), balanceAfterReplenish);
    }

    @ParameterizedTest
    @MethodSource("getFailureReplenish")
    public void testReplenishAccountReplenishFailure(BigDecimal replenishAmount) {
        String username = "mamamiya";
        assertThrows(ReplenishException.class, () ->
                accountService.replenishAccount(username, replenishAmount));
    }

    private static Stream<BigDecimal> getFailureReplenish() {
        return Stream.of(BigDecimal.ZERO, BigDecimal.valueOf(-0.1),
                BigDecimal.valueOf(-1), null);
    }

    @Test
    public void testReplenishAccountNotFound() {
        String username = "notfound";
        BigDecimal replenishAmount = BigDecimal.valueOf(100);
        assertThrows(EntityNotFoundException.class, () ->
                accountService.replenishAccount(username, replenishAmount));
    }

    @Test
    public void testBuyClothesSuccess() {
        String username = "adminito25";
        Account account = new Account(username);
        account.setBalance(BigDecimal.valueOf(22));
        BigDecimal bagTotalPrice = BigDecimal.valueOf(15);
        BigDecimal expectedBalance = account.getBalance().subtract(bagTotalPrice);

        when(bagServiceFeignClient.getBagTotalPrice(anyString())).thenReturn(bagTotalPrice);
        when(mockAccountRepository.findByUsername(anyString())).thenReturn(Optional.of(account));
        when(mockAccountRepository.save(account)).thenReturn(account);
        Account actual = mockAccountService.buyClothes(username);
        assertEquals(expectedBalance, actual.getBalance());

        verify(bagServiceFeignClient, times(1)).getBagTotalPrice(username);
        verify(mockAccountRepository, times(1)).findByUsername(username);
        verify(mockAccountRepository, times(1)).save(account);
    }

    @Test
    public void testBuyClothesFeignExc() {
        String username = "adminito25";

        when(bagServiceFeignClient.getBagTotalPrice(anyString())).thenThrow(FeignException.class);
        assertThrows(FeignException.class, () -> mockAccountService.buyClothes(username));

        verify(bagServiceFeignClient, times(1)).getBagTotalPrice(username);
        verify(mockAccountRepository, times(0)).findByUsername(anyString());
        verify(mockAccountRepository, times(0)).save(any());
    }

    @Test
    public void testBuyClothesBalanceAfterDebitSmallerThanZero() {
        String username = "userito";
        Account account = new Account(username);
        account.setBalance(BigDecimal.valueOf(0));
        BigDecimal bagTotalPrice = BigDecimal.valueOf(1);


        when(bagServiceFeignClient.getBagTotalPrice(anyString())).thenReturn(bagTotalPrice);
        when(mockAccountRepository.findByUsername(anyString())).thenReturn(Optional.of(account));
        when(mockAccountRepository.save(account)).thenReturn(account);
        assertThrows(BalanceException.class, () -> mockAccountService.buyClothes(username));

        verify(bagServiceFeignClient, times(1)).getBagTotalPrice(username);
        verify(mockAccountRepository, times(1)).findByUsername(anyString());
        verify(mockAccountRepository, times(0)).save(any());
    }
}
