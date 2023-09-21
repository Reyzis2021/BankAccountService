package com.reyzis.account.service;

import com.reyzis.account.converter.BankAccountConverter;
import com.reyzis.account.dto.BankAccountResponse;
import com.reyzis.account.model.BankAccount;
import com.reyzis.account.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountCreator {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountConverter bankAccountConverter;

    public BankAccountResponse create(String bankAccountName, String bankAccountPin) {
        log.info("Start bank account = {} creation operation.", bankAccountName);
        BankAccount bankAccount = BankAccount.builder()
                .bankAccountName(bankAccountName)
                .bankAccountPin(bankAccountPin)
                .bankAccountBalance(BigDecimal.ZERO)
                .build();

        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        UUID bankAccountNum = savedBankAccount.getBankAccountNum();
        log.info("Bank account creation operation complete successfully, bankAccountNum = {}.", bankAccountNum);

        return bankAccountConverter.toDto(savedBankAccount);
    }

}
