package com.example.quicktransfer.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quicktransfer.dto.CreateTransferRequest;
import com.example.quicktransfer.dto.PagedTransferResponse;
import com.example.quicktransfer.dto.TransactionContext;
import com.example.quicktransfer.dto.TransactionHistoryResponse;
import com.example.quicktransfer.dto.TransferResponse;
import com.example.quicktransfer.dto.UpdateTransferStatusRequest;
import com.example.quicktransfer.enums.TransferStatus;
import com.example.quicktransfer.service.MoneyTransferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class MoneyTransferController {

	private final MoneyTransferService moneyTransferService;

	@PostMapping
	public ResponseEntity<TransferResponse> createTransfer(

			@RequestHeader("X-Store-Id") String storeId,

			@RequestHeader("X-Register-Id") String registerId,

			@RequestHeader("X-Operator-Id") String operatorId,

			@RequestHeader(value = "X-Rquid", required = false) String rquid,

			@Valid @RequestBody CreateTransferRequest request) {

		TransactionContext context = TransactionContext.builder().storeId(storeId).registerId(registerId)
				.operatorId(operatorId).rquid(rquid).build();

		TransferResponse response = moneyTransferService.createTransfer(request, context);

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
	public ResponseEntity<TransferResponse> updateTransferStatus(

			@RequestHeader("X-Store-Id") String storeId,

			@RequestHeader("X-Register-Id") String registerId,

			@RequestHeader("X-Operator-Id") String operatorId,

			@RequestHeader(value = "X-Rquid", required = false) String rquid,

			@PathVariable Long transactionId,

			@RequestBody UpdateTransferStatusRequest request) {

		TransactionContext context = TransactionContext.builder().storeId(storeId).registerId(registerId)
				.operatorId(operatorId).rquid(rquid).build();

		return ResponseEntity.ok(moneyTransferService.updateTransferStatus(transactionId, request, context));
	}

	@GetMapping("/{transactionId}/history")
	public ResponseEntity<TransactionHistoryResponse> getTransactionHistory(@PathVariable Long transactionId) {

		return ResponseEntity.ok(moneyTransferService.getTransactionHistory(transactionId));
	}

	@GetMapping
	public ResponseEntity<PagedTransferResponse> searchTransfers(

			@RequestParam(required = false) String referenceNumber,

			@RequestParam(required = false) Long customerId,

			@RequestParam(required = false) TransferStatus status,

			@RequestParam(required = false) String storeId,

			@RequestParam(required = false) String registerId,

			@RequestParam(required = false) String destinationCountry,

			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,

			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,

			@RequestParam(defaultValue = "0") int page,

			@RequestParam(defaultValue = "10") int size) {

		return ResponseEntity.ok(moneyTransferService.searchTransfers(referenceNumber, customerId, status, storeId,
				registerId, destinationCountry, fromDate, toDate, page, size));
	}
}