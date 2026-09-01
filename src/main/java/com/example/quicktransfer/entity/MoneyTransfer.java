package com.example.quicktransfer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.quicktransfer.enums.TransferStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "MONEY_TRANSFER")
public class MoneyTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;
	
	private String referenceNumber;
	private String receiverName;
	private String destinationCountry;
	private BigDecimal transferAmount;
	private String currency;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "transfer_status")
	private TransferStatus transferStatus;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id",
	nullable = false)
	private Customer customer;
	
	@Column(name = "store_id", nullable = false)
	private String storeId;

	@Column(name = "register_id", nullable = false)
	private String registerId;

	@Column(name = "operator_id", nullable = false)
	private String operatorId;

	@Column(name = "rquid", nullable = false)
	private String rquid;
	
}