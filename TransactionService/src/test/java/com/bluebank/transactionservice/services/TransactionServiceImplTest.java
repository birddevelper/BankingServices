package com.bluebank.transactionservice.services;

import com.bluebank.transactionservice.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTest.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTest.sql")
@ActiveProfiles("test")
class TransactionServiceImplTest {


    @Autowired
    TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getTransactionsForExistingAccount() {

        List<Transaction> transactions = transactionService.getTransactions(12345678);
        assertEquals(2,transactions.size());

    }

    @Test
    void getTransactionsForNotExistingAccount() {

        List<Transaction> transactions = transactionService.getTransactions(11311178);
        assertEquals(0,transactions.size());

    }

    @Test
    void getBalanceForExistingAccount() {
      float balance = transactionService.getBalance(12345678);
      assertEquals(60,balance);
    }

    @Test
    void getBalanceForNotExistingAccount() {
        float balance = transactionService.getBalance(12111678);
        assertEquals(0,balance);
    }


    @Test
    void newTransaction() throws ParseException {
        Date transactionDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-06-27 14:33:11");
        Transaction transaction = new Transaction(66351178,100,0,transactionDate,"Test transaction");
        transactionService.newTransaction(transaction);
        List<Transaction> transactions = transactionService.getTransactions(66351178);
        assertEquals(1,transactions.size());
    }
}