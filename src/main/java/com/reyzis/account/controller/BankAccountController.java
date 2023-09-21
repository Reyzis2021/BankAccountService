package com.reyzis.account.controller;

import com.reyzis.account.api.BankAccountApi;
import com.reyzis.account.common.ApiResponse;
import com.reyzis.account.dto.BankAccountResponse;
import com.reyzis.account.dto.CreateBankAccountRequest;
import com.reyzis.account.dto.TransactionRequest;
import com.reyzis.account.dto.TransferRequest;
import com.reyzis.account.enums.BankAccountOperations;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
@Validated
public class BankAccountController {

    private final BankAccountApi bankAccountApi;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BankAccountResponse> createBankAccount(@RequestBody @Valid CreateBankAccountRequest createBankAccountRequest) {
       BankAccountResponse createdBankAccount = bankAccountApi.createBankAccount(createBankAccountRequest);

        return ApiResponse.<BankAccountResponse>builder()
                .data(createdBankAccount)
                .description(BankAccountOperations.CREATE.getDescription())
                .httpStatusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PostMapping("/{bankAccountName}/deposit")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deposit(@PathVariable String bankAccountName,
                                     @RequestBody @Valid TransactionRequest transactionRequest) {

        bankAccountApi.deposit(bankAccountName, transactionRequest);

        return ApiResponse.<Void>builder()
                .description(BankAccountOperations.DEPOSIT.getDescription())
                .httpStatusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PostMapping("/{bankAccountName}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> withdraw(@PathVariable String bankAccountName,
                                      @RequestBody @Valid TransactionRequest transactionRequest) {

        bankAccountApi.withdraw(bankAccountName, transactionRequest);

        return ApiResponse.<Void>builder()
                .description(BankAccountOperations.WITHDRAW.getDescription())
                .httpStatusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> transfer(@RequestBody @Valid TransferRequest transferRequest) {
        bankAccountApi.transfer(transferRequest);

        return ApiResponse.<Void>builder()
                .description(BankAccountOperations.TRANSFER.getDescription())
                .httpStatusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<BankAccountResponse>> getAllBankAccounts() {
        List<BankAccountResponse> retrievedBankAccounts = bankAccountApi.getAllBankAccounts();

        return ApiResponse.<List<BankAccountResponse>>builder()
                .data(retrievedBankAccounts)
                .description(BankAccountOperations.RETRIEVE.getDescription())
                .httpStatusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/{bankAccountName}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BankAccountResponse> getBankAccountByName(@PathVariable String bankAccountName) {
        BankAccountResponse retrievedBankAccount = bankAccountApi.getBankAccountByName(bankAccountName);

        return ApiResponse.<BankAccountResponse>builder()
                .data(retrievedBankAccount)
                .description(BankAccountOperations.RETRIEVE.getDescription())
                .httpStatusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
