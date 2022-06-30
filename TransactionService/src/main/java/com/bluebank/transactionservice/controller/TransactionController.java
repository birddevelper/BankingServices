package com.bluebank.transactionservice.controller;

import com.bluebank.transactionservice.models.Transaction;
import com.bluebank.transactionservice.models.responseModel.AccountBalanceResponse;
import com.bluebank.transactionservice.models.responseModel.ErrorResponse;
import com.bluebank.transactionservice.services.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionServiceImpl transactionService;

    // GET /api/transactions?accountNumber={accountNumber} - Get transactions of the account
    @Operation(summary = "Get Account's transaction records by its accountNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions successfully retrieved",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Transaction.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid account number",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/getTransactionsByAccountNumber", produces = { "application/json" })
    public ResponseEntity<List<Transaction>> getTransactions(@Parameter(description = "Account number")  @RequestParam int accountNumber) {

        try {
            if (String.valueOf(accountNumber).length() != 8) {
                ErrorResponse errorResponse = new ErrorResponse("Invalid Account number",
                        "The provided account number is not valid.");
                return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
            }

            List<Transaction> transactions =  transactionService.getTransactions(accountNumber);

            return new ResponseEntity(transactions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("َInternal Server Error",
                    "Something wrong happened");
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    // GET /api/getBalanceByAccountNumber?accountNumber={accountNumber} - Get balance of the account
    @Operation(summary = "Get Account's balance by its accountNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance successfully retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountBalanceResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid account number",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/getBalanceByAccountNumber", produces = { "application/json" })
    public ResponseEntity<AccountBalanceResponse> getAccountBalance(@Parameter(description = "Account number")  @RequestParam int accountNumber) {

        try {
            if (String.valueOf(accountNumber).length() != 8) {
                ErrorResponse errorResponse = new ErrorResponse("Invalid Account number",
                        "The provided account number is not valid.");
                return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
            }

            float balance =  transactionService.getBalance(accountNumber);

            AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse(accountNumber,balance);

            return new ResponseEntity(accountBalanceResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("َInternal Server Error",
                    "Something wrong happened");
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
