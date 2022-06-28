package com.bluebank.accountservice.repositories;

import com.bluebank.accountservice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
