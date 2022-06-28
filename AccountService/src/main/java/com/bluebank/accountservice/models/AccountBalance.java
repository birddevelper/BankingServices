package com.bluebank.accountservice.models;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AccountBalance {

    private int account;
    private float balance;
}
