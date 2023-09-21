package com.reyzis.account.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TransferRequest(

        @NotEmpty(message = "sourceBankAccountName is required attribute")
        @Size(message = "sourceBankAccountName size it should be greater when 3 and less when 32", min = 3, max = 32)
        @Pattern(message = "sourceBankAccountName must consist of numbers and letters only", regexp = "[A-Za-z0-9]+")
        String sourceBankAccountName,

        @NotEmpty(message = "sourceBankAccountPin is required attribute")
        @Size(message = "sourceBankAccountPin size it should be equals to 4", min = 4, max = 4)
        @Pattern(message = "sourceBankAccountPin must consist of numbers only", regexp = "[0-9]+")
        String sourceBankAccountPin,

        @NotEmpty(message = "targetBankAccountName is required attribute")
        @Size(message = "targetBankAccountName size it should be greater when 3 and less when 32", min = 3, max = 32)
        @Pattern(message = "targetBankAccountName must consist of numbers and letters only", regexp = "[A-Za-z0-9]+")
        String targetBankAccountName,

        @NotNull(message = "amount is required attribute")
        BigDecimal amount
) {
}
