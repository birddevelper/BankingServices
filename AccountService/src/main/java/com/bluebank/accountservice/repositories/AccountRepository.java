package com.bluebank.accountservice.repositories;

import com.bluebank.accountservice.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    Optional<Account> findByAccountNumberEquals(int accountNumber);
}
