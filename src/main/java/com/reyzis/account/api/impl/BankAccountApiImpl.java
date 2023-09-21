package com.reyzis.account.api.impl;

import com.reyzis.account.api.BankAccountApi;
import com.reyzis.account.dto.BankAccountResponse;
import com.reyzis.account.dto.TransactionRequest;
import com.reyzis.account.dto.TransferRequest;
import com.reyzis.account.service.*;
import com.reyzis.account.dto.CreateBankAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountApiImpl implements BankAccountApi {

    private final BankAccountCreator bankAccountCreator;
    private final BankAccountDepositProcessor bankAccountDepositProcessor;
    private final BankAccountWithdrawProcessor bankAccountWithdrawProcessor;
    private final BankAccountTransferProcessor bankAccountTransferProcessor;
    private final BankAccountRetriever bankAccountRetriever;

    @Override
    public BankAccountResponse createBankAccount(CreateBankAccountRequest createBankAccountRequest) {
        return bankAccountCreator.create(createBankAccountRequest.bankAccountName(),
                createBankAccountRequest.bankAccountPin());
    }

    @Override
    public BankAccountResponse getBankAccountByName(String bankAccountName) {
        return bankAccountRetriever.getBankAccountByName(bankAccountName);
    }

    @Override
    public void deposit(String bankAccountName, TransactionRequest transactionRequest) {
        bankAccountDepositProcessor.deposit(bankAccountName, transactionRequest.bankAccountPin(),
                transactionRequest.amount());
    }

    @Override
    public void withdraw(String bankAccountName, TransactionRequest transactionRequest) {
        bankAccountWithdrawProcessor.withdraw(bankAccountName, transactionRequest.bankAccountPin(),
                transactionRequest.amount());
    }

    @Override
    public List<BankAccountResponse> getAllBankAccounts() {
        return bankAccountRetriever.getAllBankAccounts();
    }

    @Override
    public void transfer(TransferRequest transferRequest) {
        bankAccountTransferProcessor.transfer(transferRequest.sourceBankAccountName(), transferRequest.sourceBankAccountPin(),
                transferRequest.targetBankAccountName(), transferRequest.amount());
    }

}
