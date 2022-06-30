package com.bluebank.transactionservice.services;

import com.bluebank.transactionservice.models.Transaction;
import com.bluebank.transactionservice.repositories.TransactionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactions(int accountNumber) {

        // retrieve all transaction of the given account from database
        return transactionRepository.findAllByAccountEquals(accountNumber);
    }

    @Override
    public float getBalance(int accountNumber) {

        // get total balance of the given account from database
        Float balance = transactionRepository.getBalanceOfAccount(accountNumber);

        // if no transaction exist for the account, it returns null, so we should return 0
        if ( balance != null) {

            return balance;
        }

        return 0;
    }

    @Override
    @RabbitListener(queues = "#{newTransactionQueue.name}")
    public void newTransaction(Transaction transaction) {
        // This function listens to message broker queue for new transaction and inserts it into database

        System.out.println("New Transaction received : " + transaction);

        // Save the received Transaction into the database
        transactionRepository.save(transaction);

    }
}
