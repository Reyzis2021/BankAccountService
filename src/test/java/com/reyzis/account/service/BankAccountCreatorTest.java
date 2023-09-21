package com.reyzis.account.service;

import com.reyzis.account.converter.BankAccountConverter;
import com.reyzis.account.dto.BankAccountResponse;
import com.reyzis.account.model.BankAccount;
import com.reyzis.account.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BankAccountCreatorTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountConverter bankAccountConverter;

    private BankAccountCreator bankAccountCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccountCreator = new BankAccountCreator(bankAccountRepository, bankAccountConverter);
    }

    @Test
    void testCreateBankAccount_Success() {
        String bankAccountName = "account1";
        String bankAccountPin = "1234";
        UUID bankAccountNum = UUID.randomUUID();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountName(bankAccountName);
        bankAccount.setBankAccountPin(bankAccountPin);
        bankAccount.setBankAccountBalance(BigDecimal.ZERO);

        BankAccount savedBankAccount = new BankAccount();
        savedBankAccount.setBankAccountNum(bankAccountNum);

        BankAccountResponse bankAccountResponse = BankAccountResponse.builder()
                .bankAccountName(bankAccountName)
                .bankAccountBalance(BigDecimal.ZERO)
                .build();

        when(bankAccountRepository.save(any())).thenReturn(savedBankAccount);
        when(bankAccountConverter.toDto(savedBankAccount)).thenReturn(bankAccountResponse);

        BankAccountResponse result = bankAccountCreator.create(bankAccountName, bankAccountPin);

        assertNotNull(result);
        assertEquals(bankAccountName, result.bankAccountName());
        verify(bankAccountRepository, times(1)).save(any());
        verify(bankAccountConverter, times(1)).toDto(savedBankAccount);
    }
}
