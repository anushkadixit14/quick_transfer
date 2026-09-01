package com.example.quicktransfer.service;

import java.time.LocalDate;

import com.example.quicktransfer.dto.CreateTransferRequest;
import com.example.quicktransfer.dto.PagedTransferResponse;
import com.example.quicktransfer.dto.TransactionContext;
import com.example.quicktransfer.dto.TransactionHistoryResponse;
import com.example.quicktransfer.dto.TransferResponse;
import com.example.quicktransfer.dto.UpdateTransferStatusRequest;
import com.example.quicktransfer.enums.TransferStatus;

public interface MoneyTransferService {

	TransferResponse createTransfer(CreateTransferRequest request, TransactionContext context);

	TransferResponse getTransferById(Long transactionId);

	TransferResponse getTransferByReferenceNumber(String referenceNumber);

	TransferResponse updateTransferStatus(Long transactionId, UpdateTransferStatusRequest request,
			TransactionContext context);

	PagedTransferResponse searchTransfers(String referenceNumber, Long customerId, TransferStatus status,
			String storeId, String registerId, String destinationCountry, LocalDate fromDate, LocalDate toDate,
			int page, int size);

	TransactionHistoryResponse getTransactionHistory(Long transactionId);
}