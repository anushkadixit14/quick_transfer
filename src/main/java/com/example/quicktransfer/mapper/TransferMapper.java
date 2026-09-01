package com.example.quicktransfer.mapper;

import org.springframework.stereotype.Component;

import com.example.quicktransfer.dto.CreateTransferRequest;
import com.example.quicktransfer.dto.TransferResponse;
import com.example.quicktransfer.entity.Customer;
import com.example.quicktransfer.entity.MoneyTransfer;

@Component
public class TransferMapper {

	public MoneyTransfer toEntity(CreateTransferRequest createTransferRequest, Customer customer) {
		MoneyTransfer moneyTransfer = new MoneyTransfer();

		moneyTransfer.setCustomer(customer);
		moneyTransfer.setReceiverName(createTransferRequest.getReceiverName());
		moneyTransfer.setDestinationCountry(createTransferRequest.getDestinationCountry());
		moneyTransfer.setTransferAmount(createTransferRequest.getTransferAmount());
		moneyTransfer.setCurrency(createTransferRequest.getCurrency());

		return moneyTransfer;

	}

	public TransferResponse toResponse(MoneyTransfer moneyTransfer) {

		return TransferResponse.builder().transactionId(moneyTransfer.getTransactionId())
				.referenceNumber(moneyTransfer.getReferenceNumber())
				.customerId(moneyTransfer.getCustomer().getCustomerId()).receiverName(moneyTransfer.getReceiverName())
				.destinationCountry(moneyTransfer.getDestinationCountry())
				.transferAmount(moneyTransfer.getTransferAmount()).currency(moneyTransfer.getCurrency())
				.status(moneyTransfer.getTransferStatus().name()).createdAt(moneyTransfer.getCreatedAt())
				.storeId(moneyTransfer.getStoreId()).registerId(moneyTransfer.getRegisterId())
				.operatorId(moneyTransfer.getOperatorId()).rquid(moneyTransfer.getRquid()).build();
	}
}
