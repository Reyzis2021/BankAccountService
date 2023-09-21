package com.reyzis.account.exception;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException {

    private final String bankAccountName;

    public InsufficientFundsException(String bankAccountName) {
        super(String.format("On bank account with name = %s insufficient funds.", bankAccountName));
        this.bankAccountName = bankAccountName;
    }
}
