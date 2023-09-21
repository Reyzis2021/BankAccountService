package com.reyzis.account.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateBankAccountRequest(

        @NotEmpty(message = "bankAccountName is required attribute")
        @Size(message = "bankAccountName size it should be greater when 3 and less when 32", min = 3, max = 32)
        @Pattern(message = "bankAccountName must consist of numbers and letters only", regexp = "[A-Za-z0-9]+")
        String bankAccountName,

        @NotEmpty(message = "bankAccountPin is required attribute")
        @Size(message = "bankAccountPin size it should be equals to 4", min = 4, max = 4)
        @Pattern(message = "bankAccountPin must consist of numbers only", regexp = "[0-9]+")
        String bankAccountPin
) {
}
