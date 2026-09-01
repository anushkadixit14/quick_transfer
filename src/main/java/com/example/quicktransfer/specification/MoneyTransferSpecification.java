package com.example.quicktransfer.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.quicktransfer.entity.MoneyTransfer;
import com.example.quicktransfer.enums.TransferStatus;

public class MoneyTransferSpecification {

	public static Specification<MoneyTransfer> hasReferenceNumber(String referenceNumber) {

		return (root, query, cb) -> referenceNumber == null ? null
				: cb.equal(root.get("referenceNumber"), referenceNumber);
	}

	public static Specification<MoneyTransfer> hasCustomerId(Long customerId) {

		return (root, query, cb) -> customerId == null ? null
				: cb.equal(root.get("customer").get("customerId"), customerId);
	}

	public static Specification<MoneyTransfer> hasStatus(TransferStatus status) {

		return (root, query, cb) -> status == null ? null : cb.equal(root.get("transferStatus"), status);
	}

	public static Specification<MoneyTransfer> hasStoreId(String storeId) {

		return (root, query, cb) -> storeId == null ? null : cb.equal(root.get("storeId"), storeId);
	}

	public static Specification<MoneyTransfer> hasRegisterId(String registerId) {

		return (root, query, cb) -> registerId == null ? null : cb.equal(root.get("registerId"), registerId);
	}

	public static Specification<MoneyTransfer> hasDestinationCountry(String destinationCountry) {

		return (root, query, cb) -> destinationCountry == null ? null
				: cb.equal(root.get("destinationCountry"), destinationCountry);
	}

	public static Specification<MoneyTransfer> createdAfter(LocalDate fromDate) {

		return (root, query, cb) -> fromDate == null ? null
				: cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate.atStartOfDay());
	}

	public static Specification<MoneyTransfer> createdBefore(LocalDate toDate) {

		return (root, query, cb) -> toDate == null ? null
				: cb.lessThanOrEqualTo(root.get("createdAt"), toDate.atTime(23, 59, 59));
	}
}