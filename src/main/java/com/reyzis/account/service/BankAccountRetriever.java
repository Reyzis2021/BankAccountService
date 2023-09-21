package com.reyzis.account.service;

import com.reyzis.account.converter.BankAccountConverter;
import com.reyzis.account.dto.BankAccountResponse;
import com.reyzis.account.exception.BankAccountNotFoundException;
import com.reyzis.account.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountRetriever {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountConverter bankAccountConverter;

    public List<BankAccountResponse> getAllBankAccounts() {
        return bankAccountRepository.findAll()
                .stream()
                .map(bankAccountConverter::toDto)
                .toList();
    }

    public BankAccountResponse getBankAccountByName(String bankAccountName) {
        return bankAccountRepository.findByBankAccountName(bankAccountName)
                .map(bankAccountConverter::toDto)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountName));
    }
}
