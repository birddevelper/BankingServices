package com.bluebank.accountservice.services;

import com.bluebank.accountservice.models.Account;

public interface AccountService {

    // Creat new account for given customer
    public Account createNewAccount(int customerID);

    // Get account information by given account number
    public Account getAccountInformation(int accountNumber);

    // Check if the account number exists
    public boolean accountExists(int accountNumber);
}
