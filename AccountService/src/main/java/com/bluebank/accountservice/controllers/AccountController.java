package com.bluebank.accountservice.controllers;


import com.bluebank.accountservice.models.Account;
import com.bluebank.accountservice.models.Customer;
import com.bluebank.accountservice.models.requestModels.NewAccountRequest;
import com.bluebank.accountservice.models.responseModels.AccountInformationResponse;
import com.bluebank.accountservice.models.responseModels.CreateAccountResponse;
import com.bluebank.accountservice.models.responseModels.ErrorResponse;
import com.bluebank.accountservice.models.Transaction;
import com.bluebank.accountservice.services.AccountServiceImpl;
import com.bluebank.accountservice.services.CustomerServiceImpl;
import com.bluebank.accountservice.services.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Endpoints for managing accounts")
public class AccountController {


    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    CustomerServiceImpl customerService;



    // POST /api/accounts (create an account)
    @Operation(summary = "Create a new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created",
                    content = { @Content(mediaType = "application/json",
                                         schema = @Schema(implementation = CreateAccountResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "Internal Error",
                    content = @Content),
            @ApiResponse(responseCode = "304", description = "Account creation failed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping( produces = { "application/json" })
    public ResponseEntity<Account> createAccount(
            @RequestBody  NewAccountRequest newAccountRequest
            ){

        try {

            // If the customer ID doesn't exist send 404 status code as response
            if (!customerService.customerExists(newAccountRequest.getCustomerID())) {
                ErrorResponse errorResponse = new ErrorResponse("Customer not found.",
                        "The provided customerID doesn't exist ");
                return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
            }

            // Open a new account connected to the given customer ID
            Account account = accountService.createNewAccount(newAccountRequest.getCustomerID());

            // If no account object is returned, send 304 status code as response
            if (account == null) {
                ErrorResponse errorResponse = new ErrorResponse("َFailed to open account",
                        "َFailed to open account for this customer.");
                return new ResponseEntity(errorResponse, HttpStatus.NOT_MODIFIED);
            }

            // If initialCredit is greater than 0 send a transaction to the Transaction service
            if (newAccountRequest.getInitialCredit() > 0) {

                // Create new transaction
                Transaction transaction = new Transaction(
                        account.getAccountNumber(),
                        newAccountRequest.getInitialCredit(),
                        0,
                        new Date(),
                        "Initial credit");

                //Send new transaction to Transaction Service
                transactionService.sendNewTransaction(transaction);
            }

            // Create response for successful account creation
            CreateAccountResponse createAccountResponse = new CreateAccountResponse(
                    account,
                    "Account successfully opened."
            );

            // Send successful account creation response
            return new ResponseEntity(createAccountResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("َInternal Server Error",
                    "Something wrong happened");
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }




    // Get account information by account number
    @Operation(summary = "Get Account Information by it's accountNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account information successfully retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountInformationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid account number",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Error",
                    content = @Content) })
    @GetMapping(value = "/{accountNumber}", produces = { "application/json" })
    public ResponseEntity<AccountInformationResponse> getAccountInformation(@Parameter(description = "Requested account number")  @PathVariable int accountNumber) {

        try {
            // If account doesn't exist send 404 status code as response
            if (accountService.accountExists(accountNumber) ){
                ErrorResponse errorResponse = new ErrorResponse("Account not found.",
                        "Requested account doesn't exist.");
                return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
            }

            // retrieve account information
            Account account = accountService.getAccountInformation(accountNumber);

            // retrieve customer information
            Customer customer = customerService.getCustomer(account.getCustomerID());

            // retrieve transactions of the account
            List<Transaction> transactions = transactionService.getTransactions(accountNumber);

            // retrieve balance of the account
            float balance = transactionService.getBalance(accountNumber);

            // create AccountInformationResponse object to be sent as response
            AccountInformationResponse accountInformationResponse = new AccountInformationResponse(
                    account.getAccountNumber(),
                    customer.getName(),
                    customer.getSureName(),
                    balance,
                    transactions);
            // send account information as response with 200 status code
            return new ResponseEntity(accountInformationResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("َInternal Server Error",
                    "Something wrong happened");
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    }
