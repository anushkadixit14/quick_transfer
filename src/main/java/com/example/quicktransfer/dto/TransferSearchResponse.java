package com.example.quicktransfer.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransferSearchResponse {

	private Long transactionId;

	private String referenceNumber;

	private String storeId;

	private String registerId;

	private String operatorId;

	private Long customerId;

	private String status;

	private BigDecimal transferAmount;

	private String currency;
}