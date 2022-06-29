package com.bluebank.transactionservice.transactionservice.services;

import com.bluebank.transactionservice.transactionservice.models.Transaction;

import java.util.List;

public interface TransactionService {

    public List<Transaction> getTransactions(int accountNumber);

    public float getBalance(int accountNumber);

    public void newTransaction(Transaction transaction);


}
