package com.example.quicktransfer.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTransferRequest {

    @NotNull(message = "Customer Id is required")
    private Long customerId;

    @NotBlank(message = "Receiver name is required")
    private String receiverName;

    @NotBlank(message = "Destination country is required")
    private String destinationCountry;

    @NotNull(message = "Transfer amount is required")
    @Positive(message = "Transfer amount must be greater than zero")
    private BigDecimal transferAmount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3,
            message = "Currency must contain exactly 3 characters")
    private String currency;
}
