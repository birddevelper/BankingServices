package com.bluebank.transactionservice.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;
    int account;
    float credit;
    float debit;
    Date transactionTime;
    String description;

    public Transaction(int account, float credit, float debit, Date transactionTime, String description) {
        this.account = account;
        this.credit = credit;
        this.debit = debit;
        this.transactionTime = transactionTime;
        this.description = description;
    }
}
