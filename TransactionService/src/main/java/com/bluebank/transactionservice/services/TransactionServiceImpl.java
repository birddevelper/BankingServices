package com.bluebank.transactionservice.services;

import com.bluebank.transactionservice.models.Transaction;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{



    @Override
    public List<Transaction> getTransactions(int accountNumber) {
        return null;
    }

    @Override
    public float getBalance(int accountNumber) {
        return 0;
    }

    @Override
    @RabbitListener(queues = "#{newTransactionQueue.name}")
    public void newTransaction(Transaction transaction) {

        System.out.println("new Transaction received : " + transaction);

    }
}
