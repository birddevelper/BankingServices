package com.bluebank.accountservice.models.responseModels;

import com.bluebank.accountservice.models.Account;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CreateAccountResponse {
    private Account account;
    private String message;
}
