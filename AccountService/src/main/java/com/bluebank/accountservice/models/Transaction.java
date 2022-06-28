package com.bluebank.accountservice.models;

import java.util.Date;

import lombok.*;


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
