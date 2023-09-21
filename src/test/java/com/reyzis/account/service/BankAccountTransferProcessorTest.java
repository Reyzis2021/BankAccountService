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

class BankAccountTransferProcessorTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    private BankAccountTransferProcessor transferProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferProcessor = new BankAccountTransferProcessor(bankAccountRepository);
    }

    @Test
    void testTransfer_Success() {
        String sourceBankAccountName = "SourceAccount";
        String sourceBankAccountPin = "1234";
        String targetBankAccountName = "TargetAccount";
        BigDecimal transferAmount = BigDecimal.valueOf(1000);

        BankAccount sourceBankAccount = new BankAccount();
        sourceBankAccount.setBankAccountName(sourceBankAccountName);
        sourceBankAccount.setBankAccountPin(sourceBankAccountPin);
        sourceBankAccount.setBankAccountBalance(BigDecimal.valueOf(5000));

        BankAccount targetBankAccount = new BankAccount();
        targetBankAccount.setBankAccountName(targetBankAccountName);
        targetBankAccount.setBankAccountBalance(BigDecimal.valueOf(3000));

        when(bankAccountRepository.findByBankAccountName(sourceBankAccountName))
                .thenReturn(Optional.of(sourceBankAccount));
        when(bankAccountRepository.findByBankAccountName(targetBankAccountName))
                .thenReturn(Optional.of(targetBankAccount));
        when(bankAccountRepository.save(any(BankAccount.class)))
                .thenReturn(sourceBankAccount, targetBankAccount);

        transferProcessor.transfer(sourceBankAccountName, sourceBankAccountPin, targetBankAccountName, transferAmount);

        assertEquals(BigDecimal.valueOf(4000), sourceBankAccount.getBankAccountBalance());
        assertEquals(BigDecimal.valueOf(4000), targetBankAccount.getBankAccountBalance());
        verify(bankAccountRepository, times(1)).findByBankAccountName(sourceBankAccountName);
        verify(bankAccountRepository, times(1)).findByBankAccountName(targetBankAccountName);
        verify(bankAccountRepository, times(2)).save(any(BankAccount.class));
    }

    @Test
    void testTransfer_InsufficientFunds_ExceptionThrown() {
        String sourceBankAccountName = "SourceAccount";
        String sourceBankAccountPin = "1234";
        String targetBankAccountName = "TargetAccount";
        BigDecimal transferAmount = BigDecimal.valueOf(10000);

        BankAccount sourceBankAccount = new BankAccount();
        sourceBankAccount.setBankAccountName(sourceBankAccountName);
        sourceBankAccount.setBankAccountPin(sourceBankAccountPin);
        sourceBankAccount.setBankAccountBalance(BigDecimal.valueOf(5000));

        BankAccount targetBankAccount = new BankAccount();
        targetBankAccount.setBankAccountName(targetBankAccountName);
        targetBankAccount.setBankAccountBalance(BigDecimal.valueOf(3000));

        when(bankAccountRepository.findByBankAccountName(sourceBankAccountName))
                .thenReturn(Optional.of(sourceBankAccount));
        when(bankAccountRepository.findByBankAccountName(targetBankAccountName))
                .thenReturn(Optional.of(targetBankAccount));

        assertThrows(InsufficientFundsException.class,
                () -> transferProcessor.transfer(sourceBankAccountName, sourceBankAccountPin, targetBankAccountName, transferAmount));

        verify(bankAccountRepository, times(1)).findByBankAccountName(sourceBankAccountName);
        verify(bankAccountRepository, times(1)).findByBankAccountName(targetBankAccountName);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void testTransfer_SourceAccountNotFound_ExceptionThrown() {
        String sourceBankAccountName = "SourceAccount";
        String sourceBankAccountPin = "1234";
        String targetBankAccountName = "TargetAccount";
        BigDecimal transferAmount = BigDecimal.valueOf(1000);

        when(bankAccountRepository.findByBankAccountName(sourceBankAccountName))
                .thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> transferProcessor.transfer(sourceBankAccountName, sourceBankAccountPin, targetBankAccountName, transferAmount));

        verify(bankAccountRepository, times(1)).findByBankAccountName(sourceBankAccountName);
        verify(bankAccountRepository, never()).findByBankAccountName(targetBankAccountName);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void testTransfer_TargetAccountNotFound_ExceptionThrown() {
        String sourceBankAccountName = "SourceAccount";
        String sourceBankAccountPin = "1234";
        String targetBankAccountName = "TargetAccount";
        BigDecimal transferAmount = BigDecimal.valueOf(1000);

        BankAccount sourceBankAccount = new BankAccount();
        sourceBankAccount.setBankAccountName(sourceBankAccountName);
        sourceBankAccount.setBankAccountPin(sourceBankAccountPin);
        sourceBankAccount.setBankAccountBalance(BigDecimal.valueOf(5000));

        BankAccount targetBankAccount = new BankAccount();
        targetBankAccount.setBankAccountName(targetBankAccountName);
        targetBankAccount.setBankAccountBalance(BigDecimal.valueOf(3000));

        when(bankAccountRepository.findByBankAccountName(sourceBankAccountName))
                .thenReturn(Optional.of(sourceBankAccount));
        when(bankAccountRepository.findByBankAccountName(targetBankAccountName))
                .thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> transferProcessor.transfer(sourceBankAccountName, sourceBankAccountPin, targetBankAccountName, transferAmount));

        verify(bankAccountRepository, times(1)).findByBankAccountName(sourceBankAccountName);
        verify(bankAccountRepository, times(1)).findByBankAccountName(targetBankAccountName);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void testTransfer_IncorrectPin_ExceptionThrown() {
        String sourceBankAccountName = "SourceAccount";
        String sourceBankAccountPin = "1234";
        String targetBankAccountName = "TargetAccount";
        BigDecimal transferAmount = BigDecimal.valueOf(1000);

        BankAccount sourceBankAccount = new BankAccount();
        sourceBankAccount.setBankAccountName(sourceBankAccountName);
        sourceBankAccount.setBankAccountPin("5678");
        sourceBankAccount.setBankAccountBalance(BigDecimal.valueOf(5000));


        BankAccount targetBankAccount = new BankAccount();
        targetBankAccount.setBankAccountName(targetBankAccountName);
        targetBankAccount.setBankAccountBalance(BigDecimal.valueOf(3000));

        when(bankAccountRepository.findByBankAccountName(sourceBankAccountName))
                .thenReturn(Optional.of(sourceBankAccount));
        when(bankAccountRepository.findByBankAccountName(targetBankAccountName))
                .thenReturn(Optional.of(targetBankAccount));

        assertThrows(IncorrectPinException.class,
                () -> transferProcessor.transfer(sourceBankAccountName, sourceBankAccountPin, targetBankAccountName, transferAmount));

        verify(bankAccountRepository, times(1)).findByBankAccountName(sourceBankAccountName);
        verify(bankAccountRepository, times(1)).findByBankAccountName(targetBankAccountName);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }
}
