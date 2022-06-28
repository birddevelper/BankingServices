package com.bluebank.accountservice.services;

import com.bluebank.accountservice.config.MessagingConfig;
import com.bluebank.accountservice.models.AccountBalance;
import com.bluebank.accountservice.models.Transaction;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.eq;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceImplTest {

    private RabbitTemplate rabbitTemplateMock;
    private TransactionService transactionService;
    private MockWebServer mockWebServer;
    private final BasicJsonTester json = new BasicJsonTester(this.getClass());
    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
        TransactionServiceImpl.GET_TRANSACTIONS_URI = mockWebServer.url("/").url().toString();
        TransactionServiceImpl.GET_BALANCE_URI = mockWebServer.url("/").url().toString();
        transactionService = new TransactionServiceImpl(rabbitTemplateMock);

    }

    @Test
    void sendNewTransaction() {

        Date transactionDate = new Date();
        Transaction transaction = new Transaction(100,1000,0,transactionDate,"Initial credit");

        transactionService.sendNewTransaction(transaction);

        Mockito.verify(this.rabbitTemplateMock)
                .convertAndSend(eq(MessagingConfig.EXCHANGE), eq(MessagingConfig.ROUTING_KEY), eq(transaction));
    }

    @Test
    void getTransactions() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("[{\"ID\" : 1," +
                                " \"account\":87654321," +
                                " \"credit\" : 100," +
                                " \"debit\" : 0," +
                                " \"transactionTime\" : \"2022-02-01T11:01:11\" ," +
                                " \"description\" :  \"Initial Credit\"" +
                                " }]")
        );


        List<Transaction> transactions = transactionService.getTransactions(87654321);

        RecordedRequest request = mockWebServer.takeRequest();

        assertEquals( "87654321", request.getRequestUrl().queryParameter("account"));

        assertEquals(100, transactions.get(0).getCredit());
        assertEquals("Initial Credit", transactions.get(0).getDescription());



    }

    @Test
    void getBalance() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"account\" : 87654321, \"balance\" : 120.0 }")
        );


        float balance = transactionService.getBalance(87654321);

        RecordedRequest request = mockWebServer.takeRequest();

        assertEquals( "87654321", request.getRequestUrl().queryParameter("account"));

        assertEquals(120.0, balance);
    }
}