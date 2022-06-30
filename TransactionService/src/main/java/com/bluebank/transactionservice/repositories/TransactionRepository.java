package com.bluebank.transactionservice.repositories;

import com.bluebank.transactionservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByAccountEquals(int accountNumber);

    @Query(value = "SELECT SUM(credit - debit) FROM transaction WHERE account = :accountNumber", nativeQuery = true)
    Float getBalanceOfAccount(@Param("accountNumber") int accountNumber);
}
