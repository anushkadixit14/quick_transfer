package com.example.quicktransfer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quicktransfer.dto.CreateTransferRequest;
import com.example.quicktransfer.dto.TransferResponse;
import com.example.quicktransfer.dto.UpdateTransferStatusRequest;
import com.example.quicktransfer.entity.Customer;
import com.example.quicktransfer.entity.MoneyTransfer;
import com.example.quicktransfer.enums.TransferStatus;
import com.example.quicktransfer.exceptions.BussinessValidationException;
import com.example.quicktransfer.exceptions.InvalidStatusTransitionException;
import com.example.quicktransfer.exceptions.ResourceNotFoundException;
import com.example.quicktransfer.mapper.TransferMapper;
import com.example.quicktransfer.repository.CustomerRepository;
import com.example.quicktransfer.repository.MoneyTransferRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MoneyTransferServiceImpl implements MoneyTransferService {
	@Autowired
	private MoneyTransferRepository moneyTransferRepository;

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private TransferMapper transferMapper;

	private String generateReferenceNumber() {

		String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

		long count = moneyTransferRepository.count() + 1;

		return "QT" + date + String.format("%04d", count);
	}

	@Override
	@Transactional
	public TransferResponse createTransfer(CreateTransferRequest request) {

		log.info("Creating transfer for customerId={}", request.getCustomerId());

		Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> {
			log.error("Customer not found. customerId={}", request.getCustomerId());
			return new ResourceNotFoundException("Customer not found");
		});

		if (!customer.getActiveFlag()) {

			log.warn("Inactive customer attempted transfer. customerId={}", customer.getCustomerId());

			throw new BussinessValidationException("Inactive customer cannot create transfer");
		}

		MoneyTransfer transfer = transferMapper.toEntity(request, customer);

		transfer.setReferenceNumber(generateReferenceNumber());

		transfer.setTransferStatus(TransferStatus.CREATED);

		LocalDateTime now = LocalDateTime.now();

		transfer.setCreatedAt(now);
		transfer.setUpdatedAt(now);

		MoneyTransfer savedTransfer = moneyTransferRepository.save(transfer);

		log.info("Transfer created successfully. transactionId={}, referenceNumber={}",
				savedTransfer.getTransactionId(), savedTransfer.getReferenceNumber());

		return transferMapper.toResponse(savedTransfer);
	}

	@Override
	public TransferResponse getTransferById(Long transactionId) {

		log.info("Fetching transfer. transactionId={}", transactionId);

		MoneyTransfer transfer = moneyTransferRepository.findById(transactionId).orElseThrow(() -> {
			log.error("Transfer not found. transactionId={}", transactionId);
			return new ResourceNotFoundException("Transfer not found");
		});

		log.info("Transfer found. transactionId={}, referenceNumber={}", transfer.getTransactionId(),
				transfer.getReferenceNumber());

		return transferMapper.toResponse(transfer);
	}

	@Override
	public TransferResponse getTransferByReferenceNumber(String referenceNumber) {

		log.info("Searching transfer by referenceNumber={}", referenceNumber);

		MoneyTransfer transfer = moneyTransferRepository.findByReferenceNumber(referenceNumber).orElseThrow(() -> {
			log.error("Transfer not found. referenceNumber={}", referenceNumber);
			return new ResourceNotFoundException("Transfer not found");
		});

		log.info("Transfer found. transactionId={}, referenceNumber={}", transfer.getTransactionId(),
				transfer.getReferenceNumber());

		return transferMapper.toResponse(transfer);
	}

	@Override
	@Transactional
	public TransferResponse updateTransferStatus(Long transactionId, UpdateTransferStatusRequest request) {

		MoneyTransfer transfer = moneyTransferRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transfer not found"));

		TransferStatus currentStatus = transfer.getTransferStatus();

		TransferStatus newStatus = request.getStatus();

		log.info("Updating transactionId={} from {} to {}", transactionId, currentStatus, newStatus);

		if (!isValidTransition(currentStatus, newStatus)) {

			log.warn("Invalid status transition. transactionId={}, from={}, to={}", transactionId, currentStatus,
					newStatus);

			throw new InvalidStatusTransitionException(
					"Transaction cannot move from " + currentStatus + " to " + newStatus);
		}

		transfer.setTransferStatus(newStatus);

		MoneyTransfer updated = moneyTransferRepository.save(transfer);

		log.info("Status updated successfully. transactionId={}, status={}", transactionId, newStatus);

		return transferMapper.toResponse(updated);
	}

	private boolean isValidTransition(TransferStatus currentStatus, TransferStatus newStatus) {

		return switch (currentStatus) {

		case CREATED -> newStatus == TransferStatus.VALIDATED || newStatus == TransferStatus.FAILED;

		case VALIDATED -> newStatus == TransferStatus.COMPLETED || newStatus == TransferStatus.FAILED;

		default -> false;
		};
	}
}
