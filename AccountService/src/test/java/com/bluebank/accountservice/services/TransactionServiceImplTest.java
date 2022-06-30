package com.bluebank.accountservice.services;

import com.bluebank.accountservice.AccountServiceApplication;
import com.bluebank.accountservice.config.MessagingConfig;
import com.bluebank.accountservice.models.AccountBalance;
import com.bluebank.accountservice.models.Transaction;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class TransactionServiceImplTest {

    private RabbitTemplate rabbitTemplateMock;
    private TransactionServiceImpl transactionService;
    private MockWebServer mockWebServer;

    @Autowired
    private Queue transactionQueue;

    @Autowired
    private DirectExchange directExchange;

    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
        String testUrl = mockWebServer.getHostName() + ":" + mockWebServer.getPort()+"/";
        transactionService = new TransactionServiceImpl(rabbitTemplateMock,transactionQueue,directExchange,testUrl,testUrl);

    }

    @Test
    void sendNewTransaction() {

        Date transactionDate = new Date();
        Transaction transaction = new Transaction(100,1000,0,transactionDate,"Initial credit");

        transactionService.sendNewTransaction(transaction);

        Mockito.verify(this.rabbitTemplateMock)
                .convertAndSend(eq(directExchange.getName()), eq(transactionQueue.getName()), eq(transaction));
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

        assertEquals( "87654321", request.getRequestUrl().queryParameter("accountNumber"));

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

        assertEquals( "87654321", request.getRequestUrl().queryParameter("accountNumber"));

        assertEquals(120.0, balance);
    }
}