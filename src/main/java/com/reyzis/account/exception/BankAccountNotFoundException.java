package com.reyzis.account.exception;

import lombok.Getter;

@Getter
public class BankAccountNotFoundException extends RuntimeException{

    private final String bankAccountName;

    public BankAccountNotFoundException(String bankAccountName) {
        super(String.format("Bank account not found by name = %s.", bankAccountName));
        this.bankAccountName = bankAccountName;
    }
}
