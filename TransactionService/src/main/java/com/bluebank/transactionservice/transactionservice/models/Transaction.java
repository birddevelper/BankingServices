package com.bluebank.transactionservice.transactionservice.models;

import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Transaction {

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
