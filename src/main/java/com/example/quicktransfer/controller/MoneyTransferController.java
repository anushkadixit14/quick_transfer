package com.example.quicktransfer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quicktransfer.dto.CreateTransferRequest;
import com.example.quicktransfer.dto.TransferResponse;
import com.example.quicktransfer.dto.UpdateTransferStatusRequest;
import com.example.quicktransfer.service.MoneyTransferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class MoneyTransferController {

	private final MoneyTransferService moneyTransferService;

	@PostMapping
	public ResponseEntity<TransferResponse> createTransfer(@Valid @RequestBody CreateTransferRequest request) {

		TransferResponse response = moneyTransferService.createTransfer(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{transactionId}")
	public ResponseEntity<TransferResponse> getTransferById(@PathVariable Long transactionId) {

		return ResponseEntity.ok(moneyTransferService.getTransferById(transactionId));
	}

	@GetMapping("/reference/{referenceNumber}")
	public ResponseEntity<TransferResponse> getTransferByReferenceNumber(@PathVariable String referenceNumber) {

		return ResponseEntity.ok(moneyTransferService.getTransferByReferenceNumber(referenceNumber));
	}

	@PatchMapping("/{transactionId}/status")
	public ResponseEntity<TransferResponse> updateTransferStatus(@PathVariable Long transactionId,
			@RequestBody UpdateTransferStatusRequest request) {

		return ResponseEntity.ok(moneyTransferService.updateTransferStatus(transactionId, request));
	}
}