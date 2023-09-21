package com.reyzis.account.exception;

import lombok.Getter;

@Getter
public class IncorrectPinException extends RuntimeException {

    private final String bankAccountPin;

    public IncorrectPinException(String bankAccountPin) {
        super(String.format("Bank account pin = %s was incorrect.", bankAccountPin));
        this.bankAccountPin = bankAccountPin;
    }
}
