package com.reyzis.account.api;

import com.reyzis.account.dto.BankAccountResponse;
import com.reyzis.account.dto.CreateBankAccountRequest;
import com.reyzis.account.dto.TransactionRequest;
import com.reyzis.account.dto.TransferRequest;
import java.util.List;

public interface BankAccountApi {

    BankAccountResponse createBankAccount(CreateBankAccountRequest createBankAccountRequest);

    List<BankAccountResponse> getAllBankAccounts();

    BankAccountResponse getBankAccountByName(String bankAccountName);

    void deposit(String bankAccountName, TransactionRequest transactionRequest);

    void withdraw(String bankAccountName, TransactionRequest transactionRequest);

    void transfer(TransferRequest transferRequest);
}
