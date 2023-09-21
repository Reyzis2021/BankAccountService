package com.reyzis.account.dto;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record BankAccountResponse(

        String bankAccountName,

        BigDecimal bankAccountBalance
) {
}
