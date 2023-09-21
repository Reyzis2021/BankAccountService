package com.reyzis.account.service;

import com.reyzis.account.exception.BankAccountNotFoundException;
import com.reyzis.account.exception.IncorrectPinException;
import com.reyzis.account.exception.InsufficientFundsException;
import com.reyzis.account.model.BankAccount;
import com.reyzis.account.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountWithdrawProcessor {

    private final BankAccountRepository bankAccountRepository;

    public void withdraw(String bankAccountName, String bankAccountPin, BigDecimal bankAccountWithdrawAmount) {
        log.info("Start bank account = {} withdraw operation process.", bankAccountName);
        bankAccountRepository.findByBankAccountName(bankAccountName)
                .ifPresentOrElse(bankAccount -> {
                            if (bankAccount.getBankAccountPin().equals(bankAccountPin)) {
                                BigDecimal currentBankAccountBalance = bankAccount.getBankAccountBalance();
                                if (currentBankAccountBalance.compareTo(bankAccountWithdrawAmount) >= 0) {
                                    bankAccount.setBankAccountBalance(currentBankAccountBalance.subtract(bankAccountWithdrawAmount));
                                    BankAccount updatedBankAccount = bankAccountRepository.save(bankAccount);
                                    log.info("Withdraw operation was successfully processed on bank account " +
                                            "with number = {}.", updatedBankAccount.getBankAccountNum());
                                } else {
                                    log.error("Withdraw operation process was failed because on bank account insufficient funds");
                                    throw new InsufficientFundsException(bankAccountName);
                                }
                            } else {
                                log.error("Withdraw operation process was failed because bank account pin was incorrect.");
                                throw new IncorrectPinException(bankAccountPin);
                            }
                        },
                        () -> {
                            log.error("Withdraw operation process was failed.");
                            throw new BankAccountNotFoundException(bankAccountPin);
                        });
    }
}
