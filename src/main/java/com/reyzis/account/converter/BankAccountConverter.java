package com.reyzis.account.converter;

import com.reyzis.account.dto.BankAccountResponse;
import com.reyzis.account.model.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountConverter {

    public BankAccountResponse toDto(BankAccount bankAccount) {
        return BankAccountResponse.builder()
                .bankAccountName(bankAccount.getBankAccountName())
                .bankAccountBalance(bankAccount.getBankAccountBalance())
                .build();
    }
}
