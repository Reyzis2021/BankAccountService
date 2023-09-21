package com.reyzis.account.service;

import com.reyzis.account.exception.BankAccountNotFoundException;
import com.reyzis.account.exception.IncorrectPinException;
import com.reyzis.account.model.BankAccount;
import com.reyzis.account.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountDepositProcessor {

    private final BankAccountRepository bankAccountRepository;

    public void deposit(String bankAccountName, String bankAccountPin, BigDecimal bankAccountDepositAmount) {
        log.info("Start bank account = {} deposit operation process.", bankAccountName);
        bankAccountRepository.findByBankAccountName(bankAccountName)
                .ifPresentOrElse(bankAccount -> {
                            if (bankAccount.getBankAccountPin().equals(bankAccountPin)) {
                                BigDecimal depositBalance = bankAccount.getBankAccountBalance().add(bankAccountDepositAmount);
                                bankAccount.setBankAccountBalance(depositBalance);
                                BankAccount updatedBankAccount = bankAccountRepository.save(bankAccount);
                                log.info("Deposit operation was successfully processed on bank account " +
                                        "with number = {}.", updatedBankAccount.getBankAccountNum());
                            } else {
                                log.error("Deposit operation process was failed because bank account pin was incorrect.");
                                throw new IncorrectPinException(bankAccountPin);
                            }
                        },
                        () -> {
                            log.error("Deposit operation process was failed because bank account not found.");
                            throw new BankAccountNotFoundException(bankAccountName);
                        });
    }
}
