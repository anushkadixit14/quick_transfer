package com.example.quicktransfer.dto;

import com.example.quicktransfer.enums.TransferStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTransferStatusRequest {

    @NotNull(message = "Status is required")
    private TransferStatus status;
}