package com.bluebank.accountservice.config;

import com.bluebank.accountservice.models.Customer;
import com.bluebank.accountservice.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("!test")
class InitiateDatabaseRecords  {

    private static final Logger log = LoggerFactory.getLogger(InitiateDatabaseRecords.class);


    @Bean
    CommandLineRunner initDatabase(CustomerRepository repository) {
        // Check if the DB is initiated or not
        if(repository.findAll().size()<1) {
            return args -> {
                try {
                    // insert initial customer info into DB
                    repository.save(new Customer(100,"Mostafa","Shaeri"));
                    repository.save(new Customer(200,"Alex", "Tailor"));
                    repository.save(new Customer(300,"Mieke", "Anderson"));

                    log.info("Initial customer records inserted into the database." );
                } catch (Exception e) {
                    log.info("Error in initiating records into database." );
                }
            };
        }
        else
            return args -> {
                log.info("DB already initiated ");
            };
    }




}