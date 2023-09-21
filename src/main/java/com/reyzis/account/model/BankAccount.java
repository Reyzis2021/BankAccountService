package com.reyzis.account.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bank_account_number")
    private UUID bankAccountNum;

    @Column(name = "bank_account_name", nullable = false, unique = true)
    private String bankAccountName;

    @Column(name = "bank_account_pin", nullable = false, unique = true)
    private String bankAccountPin;

    @Column(name = "bank_account_balance", nullable = false)
    private BigDecimal bankAccountBalance;

}
