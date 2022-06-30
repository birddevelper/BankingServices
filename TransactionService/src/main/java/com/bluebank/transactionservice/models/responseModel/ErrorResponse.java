package com.bluebank.transactionservice.models.responseModel;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ErrorResponse {

    private String error;
    private String description;
}
