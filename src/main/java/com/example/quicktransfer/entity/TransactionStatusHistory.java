package com.example.quicktransfer.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transaction_status_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private Long historyId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id", nullable = false)
	private MoneyTransfer moneyTransfer;

	@Column(name = "old_status")
	private String oldStatus;

	@Column(name = "new_status")
	private String newStatus;

	@Column(name = "store_id")
	private String storeId;

	@Column(name = "register_id")
	private String registerId;

	@Column(name = "operator_id")
	private String operatorId;

	@Column(name = "rquid")
	private String rquid;

	@Column(name = "changed_at")
	private LocalDateTime changedAt;

	private String remarks;
}