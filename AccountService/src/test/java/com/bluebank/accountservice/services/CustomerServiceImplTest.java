package com.bluebank.accountservice.services;

import com.bluebank.accountservice.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTest.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTest.sql")
@ActiveProfiles("test")
class CustomerServiceImplTest {

    @Resource
    CustomerService customerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void customerExistsForExistingCustomer() {

        boolean exists = customerService.customerExists(100);
        assertTrue(exists);
        exists = customerService.customerExists(200);
        assertTrue(exists);
    }

    @Test
    void customerExistsForNotExistingCustomer() {

        boolean exists = customerService.customerExists(700);
        assertFalse(exists);
        exists = customerService.customerExists(801);
        assertFalse(exists);
    }

    @Test
    void getCustomerForExistingCustomer() {
        Customer customer = customerService.getCustomer(100);
        Customer expectedCustomer = new Customer(100,"Mostafa","Shaeri");
        assertEquals(expectedCustomer, customer);
    }

    @Test
    void getCustomerForNotExistingCustomer() {
        Customer customer = customerService.getCustomer(800);
        assertEquals(null, customer);
    }

}