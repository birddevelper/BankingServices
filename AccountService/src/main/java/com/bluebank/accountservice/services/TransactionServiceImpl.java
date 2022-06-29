package com.bluebank.accountservice.services;

import com.bluebank.accountservice.config.MessagingConfig;
import com.bluebank.accountservice.models.AccountBalance;
import com.bluebank.accountservice.models.Transaction;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{




    @Value("${transaction-service.get-transactions-url}")
    public  String GET_TRANSACTIONS_URI;
    @Value("${transaction-service.get-balance-url}")
    public  String GET_BALANCE_URI;

    private RabbitTemplate rabbitmq;
    private WebClient webClient;

    @Autowired
    private Queue transactionQueue;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    public TransactionServiceImpl(RabbitTemplate rabbitMQ) {

        rabbitmq = rabbitMQ;
        webClient = WebClient.create();
    }

    @Override
    public void sendNewTransaction(Transaction transaction) {

        rabbitmq.convertAndSend(directExchange.getName(), transactionQueue.getName(), transaction);

    }

    @Override
    public List<Transaction> getTransactions(int accountNumber) {

      return  webClient.get()
                .uri( GET_TRANSACTIONS_URI+ "?account=" + String.valueOf(accountNumber) )
                .retrieve()
                .bodyToFlux(Transaction.class).collectList().block();
    }

    @Override
    public float getBalance(int accountNumber) {

        AccountBalance accountBalance =  webClient.get()
                .uri(GET_BALANCE_URI+ "?account=" + String.valueOf(accountNumber))
                .retrieve()
                .bodyToMono(AccountBalance.class).block();

        return accountBalance.getBalance();
    }
}
