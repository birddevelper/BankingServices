package com.bluebank.accountservice.models.responseModels;

import com.bluebank.accountservice.models.Transaction;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AccountInformationResponse {

    private int accountNumber;
    private String name;
    private String sureName;
    private float balance;
    private List<Transaction> transactions;

}
