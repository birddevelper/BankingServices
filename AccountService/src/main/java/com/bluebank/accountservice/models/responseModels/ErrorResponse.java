package com.bluebank.accountservice.models.responseModels;

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
