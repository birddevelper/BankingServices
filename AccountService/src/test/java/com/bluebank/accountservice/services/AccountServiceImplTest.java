package com.bluebank.accountservice.services;

import com.bluebank.accountservice.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@Transactional
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTest.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTest.sql")
class AccountServiceImplTest {

    @Resource
    AccountService accountService;

    @Test
    void createNewAccountForCustomer() {
        Account account = accountService.createNewAccount(100);
        assertEquals(8, String.valueOf(account.getAccountNumber()).length());
    }


    @Test
    void getExistingAccountInformation() throws ParseException {
        Account account = accountService.getAccountInformation(12345678);
        Date creationDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-06-27 14:33:11");
        Account expectedAccount = new Account(12345678,100,creationDate);
        assertEquals(expectedAccount,account);
    }


    @Test
    void getNotExistingAccountInformation() throws ParseException {
        Account account = accountService.getAccountInformation(87654906);
        assertEquals(null,account);
    }
}