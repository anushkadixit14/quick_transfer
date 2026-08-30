package com.example.quicktransfer.entity;

import static jakarta.persistence.GenerationType.IDENTITY;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long customerId;

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private LocalDate dateOfBirth;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean activeFlag;

	@OneToMany(mappedBy = "customer",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	private List<MoneyTransfer> transfers = new ArrayList<>();
}

