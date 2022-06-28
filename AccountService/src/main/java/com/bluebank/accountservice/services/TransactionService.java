package com.bluebank.accountservice.services;

import com.bluebank.accountservice.models.AccountBalance;
import com.bluebank.accountservice.models.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    // This method send a new transaction information to the Transaction service
    public void sendNewTransaction(Transaction transaction);

    // This method communicate with the Transaction service and retrieves transactions of the given account
    public List<Transaction> getTransactions(int accountNumber);

    public float getBalance(int accountNumber);
}
