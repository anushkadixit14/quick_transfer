package com.example.quicktransfer.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryItem {

    private String oldStatus;

    private String newStatus;

    private String storeId;

    private String registerId;

    private String operatorId;

    private String rquid;

    private LocalDateTime changedAt;

    private String remarks;
}