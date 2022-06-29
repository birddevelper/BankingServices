package com.bluebank.transactionservice.transactionservice.repositories;

import com.bluebank.transactionservice.transactionservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
