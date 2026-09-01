package com.example.quicktransfer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransferResponse {
	private Long transactionId;
	private String referenceNumber;
	private Long customerId;
	private String receiverName;
	private String destinationCountry;
	private BigDecimal transferAmount;
	private String currency;
	private String status;
	private LocalDateTime createdAt;
	
	private String storeId;
	private String registerId;
	private String operatorId;
	private String rquid;
}
