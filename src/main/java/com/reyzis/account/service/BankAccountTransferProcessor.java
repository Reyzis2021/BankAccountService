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
public class BankAccountTransferProcessor {

    private final BankAccountRepository bankAccountRepository;

    public void transfer(String sourceBankAccountName, String sourceBankAccountPin, String targetBankAccountName, BigDecimal transferAmount) {
        log.info("Start transfer operation process between source bank account = {} " +
                "and target bank account = {}.", sourceBankAccountName, targetBankAccountName);

        BankAccount sourceBankAccount = bankAccountRepository.findByBankAccountName(sourceBankAccountName)
                .orElseThrow(() -> {
                    log.error("Transfer operation process was failed because source bank account not found");
                    throw new BankAccountNotFoundException(sourceBankAccountName);
                });

        BankAccount targetBankAccount = bankAccountRepository.findByBankAccountName(targetBankAccountName)
                .orElseThrow(() -> {
                    log.error("Transfer operation process was failed because target bank account not found");
                    throw new BankAccountNotFoundException(targetBankAccountName);
                });

        if (sourceBankAccount.getBankAccountPin().equals(sourceBankAccountPin)) {
            BigDecimal sourceBankAccountBalance = sourceBankAccount.getBankAccountBalance();

            if (sourceBankAccountBalance.compareTo(transferAmount) >= 0) {
                sourceBankAccount.setBankAccountBalance(sourceBankAccountBalance.subtract(transferAmount));
                BigDecimal targetBankAccountBalance = targetBankAccount.getBankAccountBalance();
                targetBankAccount.setBankAccountBalance(targetBankAccountBalance.add(transferAmount));

                BankAccount updatedSourceBankAccount = bankAccountRepository.save(sourceBankAccount);
                log.info("Transfer operation was successfully processed on source bank account " +
                        "with number = {}.", updatedSourceBankAccount.getBankAccountNum());

                BankAccount updatedTargetBankAccount = bankAccountRepository.save(targetBankAccount);
                log.info("Transfer operation was successfully processed on target bank account " +
                        "with number = {}.", updatedTargetBankAccount.getBankAccountNum());
            } else {
                log.error("Transfer operation process was failed because on source bank account insufficient funds.");
                throw new InsufficientFundsException(sourceBankAccountName);
            }
        } else {
            log.error("Transfer operation process was failed because source bank account pin was incorrect.");
            throw new IncorrectPinException(sourceBankAccountPin);
        }
    }
}
