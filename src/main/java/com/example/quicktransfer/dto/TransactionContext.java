package com.example.quicktransfer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionContext {

	private String storeId;

	private String registerId;

	private String operatorId;

	private String rquid;
}