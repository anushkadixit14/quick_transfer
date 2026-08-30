package com.example.quicktransfer.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quicktransfer.entity.MoneyTransfer;

@Repository
public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, Long>{

	Optional<MoneyTransfer> findByReferenceNumber(String referenceNumber);
	
}
