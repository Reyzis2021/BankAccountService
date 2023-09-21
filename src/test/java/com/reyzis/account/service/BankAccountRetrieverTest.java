package com.reyzis.account.service;

import com.reyzis.account.converter.BankAccountConverter;
import com.reyzis.account.dto.BankAccountResponse;
import com.reyzis.account.exception.BankAccountNotFoundException;
import com.reyzis.account.model.BankAccount;
import com.reyzis.account.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountRetrieverTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountConverter bankAccountConverter;

    private BankAccountRetriever bankAccountRetriever;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccountRetriever = new BankAccountRetriever(bankAccountRepository, bankAccountConverter);
    }

    @Test
    void testGetAllBankAccounts() {
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setBankAccountName("account1");

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setBankAccountName("account2");

        List<BankAccount> bankAccounts = Arrays.asList(bankAccount1, bankAccount2);

        BankAccountResponse bankAccountResponse1 = BankAccountResponse.builder()
                .bankAccountName("account1")
                .build();

        BankAccountResponse bankAccountResponse2 = BankAccountResponse.builder()
                .bankAccountName("account2")
                .build();

        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
        when(bankAccountConverter.toDto(bankAccount1)).thenReturn(bankAccountResponse1);
        when(bankAccountConverter.toDto(bankAccount2)).thenReturn(bankAccountResponse2);

        List<BankAccountResponse> result = bankAccountRetriever.getAllBankAccounts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("account1", result.get(0).bankAccountName());
        assertEquals("account2", result.get(1).bankAccountName());
        verify(bankAccountRepository, times(1)).findAll();
        verify(bankAccountConverter, times(1)).toDto(bankAccount1);
        verify(bankAccountConverter, times(1)).toDto(bankAccount2);
    }

    @Test
    void testGetBankAccountByName() {
        String bankAccountName = "account1";

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountName(bankAccountName);

        BankAccountResponse bankAccountResponse =  BankAccountResponse.builder()
                .bankAccountName(bankAccountName)
                .build();

        when(bankAccountRepository.findByBankAccountName(bankAccountName)).thenReturn(Optional.of(bankAccount));
        when(bankAccountConverter.toDto(bankAccount)).thenReturn(bankAccountResponse);

        BankAccountResponse result = bankAccountRetriever.getBankAccountByName(bankAccountName);

        assertNotNull(result);
        assertEquals("account1", result.bankAccountName());
        verify(bankAccountRepository, times(1)).findByBankAccountName(bankAccountName);
        verify(bankAccountConverter, times(1)).toDto(bankAccount);
    }

    @Test
    void testGetBankAccountByNameNotFound() {
        String bankAccountName = "nonexistent";

        when(bankAccountRepository.findByBankAccountName(bankAccountName)).thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> bankAccountRetriever.getBankAccountByName(bankAccountName));
    }
}
