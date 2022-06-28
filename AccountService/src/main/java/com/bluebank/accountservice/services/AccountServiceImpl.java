package com.bluebank.accountservice.services;

import com.bluebank.accountservice.models.Account;
import com.bluebank.accountservice.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    AccountRepository accountRepository;




    // Creat new account for given customer by ID
    @Override
    public Account createNewAccount(int customerID) {

        // Get current date time
        Date date = new Date();

        // Generate random account number
        Random rnd = new Random();
        int accountNumber = 10000000 + rnd.nextInt(90000000);
        // While the generated account number is already existed, regenerate account number
        while (accountExists(accountNumber))
            accountNumber = 10000000 + rnd.nextInt(90000000);

        Account account = new Account(accountNumber,customerID,date);

        // Save Account into Database
        if (accountRepository.save(account) != null ) {
            return account;
        }

        return null;
    }

    // Get account information by given account number
    @Override
    public Account getAccountInformation(int accountNumber) {
            Optional<Account> account = accountRepository.findById(accountNumber);
            if(account.isPresent())
                return account.get();

            return null;
    }

    // Check if the account number exists
    @Override
    public boolean accountExists(int accountNumber) {
        return accountRepository.findById(accountNumber).isPresent();
    }
}
