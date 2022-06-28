package com.bluebank.accountservice.services;

import com.bluebank.accountservice.models.Customer;
import com.bluebank.accountservice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    // Check whether the customerID exist or not
    @Override
    public boolean customerExists(int customerID) {

        return customerRepository.findById(customerID).isPresent();

    }

    // get customer information by customer ID
    @Override
    public Customer getCustomer(int customerID) {
        Optional<Customer> customer = customerRepository.findById(customerID);
        if(customer.isPresent())
            return customer.get();
        return null;
    }
}
