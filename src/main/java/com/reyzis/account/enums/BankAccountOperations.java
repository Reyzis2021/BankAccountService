package com.reyzis.account.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankAccountOperations {

    CREATE("Bank account creation operation was successfully processed."),
    DEPOSIT("Bank account deposit operation was successfully processed."),
    WITHDRAW("Bank account withdraw operation was successfully processed."),
    TRANSFER("Bank account transfer operation was successfully processed."),
    RETRIEVE("Bank account retrieve operation was successfully processed.");

    private final String description;
}
