package com.reyzis.account.repository;

import com.reyzis.account.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    Optional<BankAccount> findByBankAccountName(String bankAccountName);
}
