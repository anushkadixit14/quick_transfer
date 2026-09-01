package com.example.quicktransfer.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponse {

    private Long transactionId;

    private String referenceNumber;

    private List<TransactionHistoryItem> history;
}