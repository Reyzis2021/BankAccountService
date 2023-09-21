package com.reyzis.account.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record TransactionRequest(

        @NotEmpty(message = "bankAccountPin is required attribute")
        @Size(message = "bankAccountPin size it should be equals to 4", min = 4, max = 4)
        @Pattern(message = "bankAccountPin must consist of numbers only", regexp = "[0-9]+")
        String bankAccountPin,

        @NotNull(message = "amount is required attribute")
        BigDecimal amount
) {
}
