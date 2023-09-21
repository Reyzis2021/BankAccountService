package com.reyzis.account.service;

import com.reyzis.account.exception.BankAccountNotFoundException;
import com.reyzis.account.exception.IncorrectPinException;
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

class BankAccountDepositProcessorTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    private BankAccountDepositProcessor depositProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        depositProcessor = new BankAccountDepositProcessor(bankAccountRepository);
    }

    @Test
    void testDeposit_Success() {
        String bankAccountName = "TestAccount";
        String bankAccountPin = "1234";
        BigDecimal bankAccountDepositAmount = BigDecimal.valueOf(1000);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountName(bankAccountName);
        bankAccount.setBankAccountPin(bankAccountPin);
        bankAccount.setBankAccountBalance(BigDecimal.valueOf(5000));

        when(bankAccountRepository.findByBankAccountName(bankAccountName))
                .thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.save(any(BankAccount.class)))
                .thenReturn(bankAccount);

        depositProcessor.deposit(bankAccountName, bankAccountPin, bankAccountDepositAmount);

        assertEquals(BigDecimal.valueOf(6000), bankAccount.getBankAccountBalance());
        verify(bankAccountRepository, times(1)).findByBankAccountName(bankAccountName);
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    void testDeposit_IncorrectPin_ExceptionThrown() {
        String bankAccountName = "TestAccount";
        String bankAccountPin = "1234";
        BigDecimal bankAccountDepositAmount = BigDecimal.valueOf(1000);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountName(bankAccountName);
        bankAccount.setBankAccountPin("5678");
        bankAccount.setBankAccountBalance(BigDecimal.valueOf(5000));

        when(bankAccountRepository.findByBankAccountName(bankAccountName))
                .thenReturn(Optional.of(bankAccount));

        assertThrows(IncorrectPinException.class,
                () -> depositProcessor.deposit(bankAccountName, bankAccountPin, bankAccountDepositAmount));

        verify(bankAccountRepository, times(1)).findByBankAccountName(bankAccountName);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void testDeposit_AccountNotFound_ExceptionThrown() {
        String bankAccountName = "TestAccount";
        String bankAccountPin = "1234";
        BigDecimal bankAccountDepositAmount = BigDecimal.valueOf(1000);

        when(bankAccountRepository.findByBankAccountName(bankAccountName))
                .thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> depositProcessor.deposit(bankAccountName, bankAccountPin, bankAccountDepositAmount));

        verify(bankAccountRepository, times(1)).findByBankAccountName(bankAccountName);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }
}

