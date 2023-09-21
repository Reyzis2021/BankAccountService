package com.reyzis.account.service;

import com.reyzis.account.exception.BankAccountNotFoundException;
import com.reyzis.account.exception.IncorrectPinException;
import com.reyzis.account.exception.InsufficientFundsException;
import com.reyzis.account.model.BankAccount;
import com.reyzis.account.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BankAccountWithdrawProcessorTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    private BankAccountWithdrawProcessor withdrawProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        withdrawProcessor = new BankAccountWithdrawProcessor(bankAccountRepository);
    }

    @Test
    void testWithdraw_Success() {
        String bankAccountName = "account1";
        String bankAccountPin = "1234";
        BigDecimal withdrawAmount = new BigDecimal("100");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountName(bankAccountName);
        bankAccount.setBankAccountPin(bankAccountPin);
        bankAccount.setBankAccountBalance(new BigDecimal("500"));

        when(bankAccountRepository.findByBankAccountName(bankAccountName)).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.save(any())).thenReturn(bankAccount);

        withdrawProcessor.withdraw(bankAccountName, bankAccountPin, withdrawAmount);

        assertEquals(new BigDecimal("400"), bankAccount.getBankAccountBalance());
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    void testWithdraw_InsufficientFunds_ExceptionThrown() {
        String bankAccountName = "account2";
        String bankAccountPin = "5678";
        BigDecimal withdrawAmount = new BigDecimal("1000");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountName(bankAccountName);
        bankAccount.setBankAccountPin(bankAccountPin);
        bankAccount.setBankAccountBalance(new BigDecimal("500"));

        when(bankAccountRepository.findByBankAccountName(bankAccountName)).thenReturn(Optional.of(bankAccount));

        assertThrows(InsufficientFundsException.class,
                () -> withdrawProcessor.withdraw(bankAccountName, bankAccountPin, withdrawAmount));
    }

    @Test
    void testWithdraw_IncorrectPin_ExceptionThrown() {
        String bankAccountName = "account3";
        String bankAccountPin = "9999";
        BigDecimal withdrawAmount = new BigDecimal("200");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountName(bankAccountName);
        bankAccount.setBankAccountPin("8888");
        bankAccount.setBankAccountBalance(new BigDecimal("1000"));

        when(bankAccountRepository.findByBankAccountName(bankAccountName)).thenReturn(Optional.of(bankAccount));

        assertThrows(IncorrectPinException.class,
                () -> withdrawProcessor.withdraw(bankAccountName, bankAccountPin, withdrawAmount));
    }

    @Test
    void testWithdraw_AccountNotFound_ExceptionThrown() {
        String bankAccountName = "TestAccount";
        String bankAccountPin = "1234";
        BigDecimal bankAccountDepositAmount = BigDecimal.valueOf(1000);

        when(bankAccountRepository.findByBankAccountName(bankAccountName))
                .thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> withdrawProcessor.withdraw(bankAccountName, bankAccountPin, bankAccountDepositAmount));

        verify(bankAccountRepository, times(1)).findByBankAccountName(bankAccountName);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }
}