package com.example.quicktransfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quicktransfer.entity.TransactionStatusHistory;

public interface TransactionStatusHistoryRepository
        extends JpaRepository<TransactionStatusHistory, Long> {

    List<TransactionStatusHistory>
            findByMoneyTransferTransactionIdOrderByChangedAtAsc(
                    Long transactionId);
    
    
}