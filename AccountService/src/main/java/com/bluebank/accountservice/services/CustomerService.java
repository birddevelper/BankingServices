package com.bluebank.accountservice.services;

import com.bluebank.accountservice.models.Customer;

public interface CustomerService {

    // Check whether the customerID exist or not
    public boolean customerExists(int customerID);

    // get customer information by customer ID
    public Customer getCustomer(int customerID);
}
