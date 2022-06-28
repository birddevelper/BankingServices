package com.bluebank.accountservice.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name="account")
@Getter
@Setter
public class Account {
    @Id
    @Column(name = "account_number")
    private int accountNumber;

    @Column(name = "customer_id")
    private int customerID;

    @Column(name = "creation_date")
    private Date creationDate;

}
