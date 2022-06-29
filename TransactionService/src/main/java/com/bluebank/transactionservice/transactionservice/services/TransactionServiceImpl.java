package com.bluebank.transactionservice.transactionservice.services;

import com.bluebank.transactionservice.transactionservice.models.Transaction;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{


    String TRANSACTION_QUEUE;


    @Override
    public List<Transaction> getTransactions(int accountNumber) {
        return null;
    }

    @Override
    public float getBalance(int accountNumber) {
        return 0;
    }

    @Override
    @RabbitListener(queues = )
    public void newTransaction(Transaction transaction) {

    }
}
