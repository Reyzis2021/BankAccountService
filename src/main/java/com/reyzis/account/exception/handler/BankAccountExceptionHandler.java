package com.reyzis.account.exception.handler;

import com.reyzis.account.common.ApiResponse;
import com.reyzis.account.exception.BankAccountNotFoundException;
import com.reyzis.account.exception.IncorrectPinException;
import com.reyzis.account.exception.InsufficientFundsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BankAccountExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleBankAccountNotFoundException(BankAccountNotFoundException exception) {
        ApiResponse<Void> apiResponse = buildResponse(exception, HttpStatus.NOT_FOUND);
        log.error("Handle bank account not found exception: messages: {}, description: {}.",
                apiResponse.messages(), apiResponse.description());

        return apiResponse;
    }

    @ExceptionHandler(IncorrectPinException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleIncorrectPinException(IncorrectPinException exception) {
        ApiResponse<Void> apiResponse = buildResponse(exception, HttpStatus.BAD_REQUEST);
        log.error("Handle incorrect pin exception: messages: {}, description: {}.",
                apiResponse.messages(), apiResponse.description());

        return apiResponse;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleInsufficientFundsException(InsufficientFundsException exception) {
        ApiResponse<Void> apiResponse = buildResponse(exception, HttpStatus.BAD_REQUEST);
        log.error("Handle insufficient funds exception: messages: {}, description: {}.",
                apiResponse.messages(), apiResponse.description());

        return apiResponse;
    }

}
