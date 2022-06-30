package com.bluebank.transactionservice.services;

import com.bluebank.transactionservice.models.Transaction;

import java.util.List;

public interface TransactionService {

    public List<Transaction> getTransactions(int accountNumber);

    public float getBalance(int accountNumber);

    public Transaction newTransaction(Transaction transaction);


}
