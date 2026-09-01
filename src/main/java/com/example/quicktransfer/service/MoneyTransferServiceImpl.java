package com.example.quicktransfer.service;

import java.time.LocalDate;
import static com.example.quicktransfer.specification.MoneyTransferSpecification.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.quicktransfer.dto.CreateTransferRequest;
import com.example.quicktransfer.dto.PagedTransferResponse;
import com.example.quicktransfer.dto.TransactionContext;
import com.example.quicktransfer.dto.TransactionHistoryItem;
import com.example.quicktransfer.dto.TransactionHistoryResponse;
import com.example.quicktransfer.dto.TransferResponse;
import com.example.quicktransfer.dto.TransferSearchResponse;
import com.example.quicktransfer.dto.UpdateTransferStatusRequest;
import com.example.quicktransfer.entity.Customer;
import com.example.quicktransfer.entity.MoneyTransfer;
import com.example.quicktransfer.entity.TransactionStatusHistory;
import com.example.quicktransfer.enums.TransferStatus;
import com.example.quicktransfer.exceptions.BussinessValidationException;
import com.example.quicktransfer.exceptions.InvalidStatusTransitionException;
import com.example.quicktransfer.exceptions.ResourceNotFoundException;
import com.example.quicktransfer.mapper.TransferMapper;
import com.example.quicktransfer.repository.CustomerRepository;
import com.example.quicktransfer.repository.MoneyTransferRepository;
import com.example.quicktransfer.repository.TransactionStatusHistoryRepository;

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
	@Autowired
	private TransactionStatusHistoryRepository transactionStatusHistoryRepository;

	private String generateReferenceNumber() {

		String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

		long count = moneyTransferRepository.count() + 1;

		return "QT" + date + String.format("%04d", count);
	}

	@Override
	@Transactional
	public TransferResponse createTransfer(CreateTransferRequest request, TransactionContext context) {
		if (context.getStoreId() == null || context.getStoreId().isBlank()) {

			throw new BussinessValidationException("Store information is required");
		}

		if (context.getRegisterId() == null || context.getRegisterId().isBlank()) {

			throw new BussinessValidationException("Register information is required");
		}

		if (context.getOperatorId() == null || context.getOperatorId().isBlank()) {

			throw new BussinessValidationException("Operator information is required");
		}

		if (context.getRquid() == null || context.getRquid().isBlank()) {

			context.setRquid(UUID.randomUUID().toString());
		}

		log.info("""
				Creating money transfer
				rquid={}
				storeId={}
				registerId={}
				operatorId={}
				customerId={}
				amount={}
				currency={}
				""", context.getRquid(), context.getStoreId(), context.getRegisterId(), context.getOperatorId(),
				request.getCustomerId(), request.getTransferAmount(), request.getCurrency());
		Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> {
			log.error("Customer not found. customerId={}", request.getCustomerId());
			return new ResourceNotFoundException("Customer not found");
		});

		if (!customer.getActiveFlag()) {

			log.warn("Inactive customer attempted transfer. customerId={}", customer.getCustomerId());

			throw new BussinessValidationException("Inactive customer cannot create transfer");
		}

		MoneyTransfer transfer = transferMapper.toEntity(request, customer);

		transfer.setStoreId(context.getStoreId());

		transfer.setRegisterId(context.getRegisterId());

		transfer.setOperatorId(context.getOperatorId());

		transfer.setRquid(context.getRquid());

		transfer.setReferenceNumber(generateReferenceNumber());

		transfer.setTransferStatus(TransferStatus.CREATED);

		LocalDateTime now = LocalDateTime.now();

		transfer.setCreatedAt(now);
		transfer.setUpdatedAt(now);

		MoneyTransfer savedTransfer = moneyTransferRepository.save(transfer);
		TransactionStatusHistory history = new TransactionStatusHistory();

		history.setMoneyTransfer(savedTransfer);

		history.setOldStatus(null);

		history.setNewStatus(TransferStatus.CREATED.name());

		history.setStoreId(context.getStoreId());

		history.setRegisterId(context.getRegisterId());

		history.setOperatorId(context.getOperatorId());

		history.setRquid(context.getRquid());

		history.setChangedAt(LocalDateTime.now());

		history.setRemarks("Transaction created");

		transactionStatusHistoryRepository.save(history);
		log.info("""
				Money transfer created
				transactionId={}
				referenceNumber={}
				rquid={}
				storeId={}
				registerId={}
				operatorId={}
				status={}
				""", savedTransfer.getTransactionId(), savedTransfer.getReferenceNumber(), savedTransfer.getRquid(),
				savedTransfer.getStoreId(), savedTransfer.getRegisterId(), savedTransfer.getOperatorId(),
				savedTransfer.getTransferStatus());

		return transferMapper.toResponse(savedTransfer);
	}

	@Override
	public TransferResponse getTransferById(Long transactionId) {

		log.info("Fetching transfer. transactionId={}", transactionId);

		MoneyTransfer transfer = moneyTransferRepository.findById(transactionId).orElseThrow(() -> {

			log.error("Transfer not found. transactionId={}", transactionId);

			return new ResourceNotFoundException("Transfer not found");
		});

		log.info("""
				Transfer fetched
				transactionId={}
				referenceNumber={}
				status={}
				storeId={}
				registerId={}
				operatorId={}
				rquid={}
				""", transfer.getTransactionId(), transfer.getReferenceNumber(), transfer.getTransferStatus(),
				transfer.getStoreId(), transfer.getRegisterId(), transfer.getOperatorId(), transfer.getRquid());

		return transferMapper.toResponse(transfer);
	}

	@Override
	public TransferResponse getTransferByReferenceNumber(String referenceNumber) {

		log.info("""
				Searching transfer
				referenceNumber={}
				""", referenceNumber);
		MoneyTransfer transfer = moneyTransferRepository.findByReferenceNumber(referenceNumber).orElseThrow(() -> {
			log.error("Transfer not found. referenceNumber={}", referenceNumber);
			return new ResourceNotFoundException("Transfer not found");
		});

		log.info("""
				Transfer fetched by reference
				transactionId={}
				referenceNumber={}
				status={}
				storeId={}
				registerId={}
				operatorId={}
				rquid={}
				""", transfer.getTransactionId(), transfer.getReferenceNumber(), transfer.getTransferStatus(),
				transfer.getStoreId(), transfer.getRegisterId(), transfer.getOperatorId(), transfer.getRquid());
		return transferMapper.toResponse(transfer);
	}

	@Override
	@Transactional
	public TransferResponse updateTransferStatus(Long transactionId, UpdateTransferStatusRequest request,
			TransactionContext context) {

		MoneyTransfer transfer = moneyTransferRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transfer not found"));

		TransferStatus currentStatus = transfer.getTransferStatus();

		TransferStatus newStatus = request.getStatus();

		log.info("""
				Updating transaction
				transactionId={}
				referenceNumber={}
				oldStatus={}
				newStatus={}
				storeId={}
				registerId={}
				operatorId={}
				rquid={}
				""", transfer.getTransactionId(), transfer.getReferenceNumber(), currentStatus, newStatus,
				context.getStoreId(), context.getRegisterId(), context.getOperatorId(), context.getRquid());

		if (!isValidTransition(currentStatus, newStatus)) {

			log.warn("""
					Invalid status transition
					transactionId={}
					referenceNumber={}
					oldStatus={}
					attemptedStatus={}
					rquid={}
					""", transfer.getTransactionId(), transfer.getReferenceNumber(), currentStatus, newStatus,
					context.getRquid());

			throw new InvalidStatusTransitionException(
					"Transaction cannot move from " + currentStatus + " to " + newStatus);
		}

		transfer.setTransferStatus(newStatus);

		transfer.setUpdatedAt(LocalDateTime.now());

		MoneyTransfer updated = moneyTransferRepository.save(transfer);

		TransactionStatusHistory history = new TransactionStatusHistory();

		history.setMoneyTransfer(updated);

		history.setOldStatus(currentStatus.name());

		history.setNewStatus(newStatus.name());

		history.setStoreId(context.getStoreId());

		history.setRegisterId(context.getRegisterId());

		history.setOperatorId(context.getOperatorId());

		history.setRquid(context.getRquid());

		history.setChangedAt(LocalDateTime.now());

		history.setRemarks(request.getRemarks());

		transactionStatusHistoryRepository.save(history);

		log.info("""
				Status updated successfully
				transactionId={}
				referenceNumber={}
				oldStatus={}
				newStatus={}
				storeId={}
				registerId={}
				operatorId={}
				rquid={}
				""", updated.getTransactionId(), updated.getReferenceNumber(), currentStatus, newStatus,
				context.getStoreId(), context.getRegisterId(), context.getOperatorId(), context.getRquid());

		return transferMapper.toResponse(updated);
	}

	private boolean isValidTransition(TransferStatus currentStatus, TransferStatus newStatus) {

		return switch (currentStatus) {

		case CREATED -> newStatus == TransferStatus.VALIDATED || newStatus == TransferStatus.FAILED;

		case VALIDATED -> newStatus == TransferStatus.COMPLETED || newStatus == TransferStatus.FAILED;

		default -> false;
		};
	}

	@Override
	public TransactionHistoryResponse getTransactionHistory(Long transactionId) {
		log.info("Fetching transaction history. transactionId={}", transactionId);

		MoneyTransfer transfer = moneyTransferRepository.findById(transactionId).orElseThrow(() -> {
			log.error("Transfer not found while fetching history. transactionId={}", transactionId);
			return new ResourceNotFoundException("Transfer not found");
		});

		List<TransactionStatusHistory> historyList = transactionStatusHistoryRepository
				.findByMoneyTransferTransactionIdOrderByChangedAtAsc(transactionId);
		log.info("Transaction history retrieved successfully. transactionId={}, referenceNumber={}, historyCount={}",
				transfer.getTransactionId(), transfer.getReferenceNumber(), historyList.size());

		List<TransactionHistoryItem> items = historyList.stream().map(history -> TransactionHistoryItem.builder()
				.oldStatus(history.getOldStatus()).newStatus(history.getNewStatus()).storeId(history.getStoreId())
				.registerId(history.getRegisterId()).operatorId(history.getOperatorId()).rquid(history.getRquid())
				.changedAt(history.getChangedAt()).remarks(history.getRemarks()).build()).toList();

		return TransactionHistoryResponse.builder().transactionId(transfer.getTransactionId())
				.referenceNumber(transfer.getReferenceNumber()).history(items).build();
	}

	@Override
	public PagedTransferResponse searchTransfers(String referenceNumber, Long customerId, TransferStatus status,
			String storeId, String registerId, String destinationCountry, LocalDate fromDate, LocalDate toDate,
			int page, int size) {
		log.info(
				"Searching transfers. referenceNumber={}, customerId={}, status={}, storeId={}, registerId={}, destinationCountry={}, fromDate={}, toDate={}, page={}, size={}",
				referenceNumber, customerId, status, storeId, registerId, destinationCountry, fromDate, toDate, page,
				size);
		size = Math.min(size, 50);

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

		Specification<MoneyTransfer> specification = Specification.where(hasReferenceNumber(referenceNumber))
				.and(hasCustomerId(customerId)).and(hasStatus(status)).and(hasStoreId(storeId))
				.and(hasRegisterId(registerId)).and(hasDestinationCountry(destinationCountry))
				.and(createdAfter(fromDate)).and(createdBefore(toDate));

		Page<MoneyTransfer> transferPage = moneyTransferRepository.findAll(specification, pageable);

		if (transferPage.isEmpty()) {
			log.info("No transfers found for search criteria. referenceNumber={}, customerId={}, status={}, storeId={}",
					referenceNumber, customerId, status, storeId);
		} else {
			log.info("Transfer search completed. totalElements={}, totalPages={}, currentPage={}, recordsReturned={}",
					transferPage.getTotalElements(), transferPage.getTotalPages(), transferPage.getNumber(),
					transferPage.getNumberOfElements());
		}

		List<TransferSearchResponse> content = transferPage.getContent().stream()
				.map(transfer -> TransferSearchResponse.builder().transactionId(transfer.getTransactionId())
						.referenceNumber(transfer.getReferenceNumber()).storeId(transfer.getStoreId())
						.registerId(transfer.getRegisterId()).operatorId(transfer.getOperatorId())
						.customerId(transfer.getCustomer().getCustomerId()).status(transfer.getTransferStatus().name())
						.transferAmount(transfer.getTransferAmount()).currency(transfer.getCurrency()).build())
				.toList();

		return PagedTransferResponse.builder().content(content).page(transferPage.getNumber())
				.size(transferPage.getSize()).totalElements(transferPage.getTotalElements())
				.totalPages(transferPage.getTotalPages()).build();
	}
}
