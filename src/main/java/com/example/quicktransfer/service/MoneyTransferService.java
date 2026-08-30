package com.example.quicktransfer.service;

import com.example.quicktransfer.dto.CreateTransferRequest;
import com.example.quicktransfer.dto.TransferResponse;
import com.example.quicktransfer.dto.UpdateTransferStatusRequest;

public interface MoneyTransferService {

	TransferResponse createTransfer(CreateTransferRequest request);

	TransferResponse getTransferById(Long transactionId);

	TransferResponse getTransferByReferenceNumber(String referenceNumber);

	TransferResponse updateTransferStatus(Long transactionId, UpdateTransferStatusRequest request);
}