package com.bluebank.transactionservice.transactionservice.models.responseModel;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AccountBalanceResponse {

    private int account;
    private float balance;
}
