package com.bluebank.accountservice.models.requestModels;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NewAccountRequest {

    @NotNull
    @Schema(example= "100")
    @Parameter(description = "The customer ID to open account for", required = true)
    private int customerID;

    @Schema(example = "23.97")
    @Parameter(description = "Initial amount of credit (default = 0)", required = false)
    private float initialCredit =0;
}
