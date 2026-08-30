package com.example.quicktransfer.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.quicktransfer.dto.CreateCustomerRequest;
import com.example.quicktransfer.dto.CustomerResponse;
import com.example.quicktransfer.entity.Customer;
import com.example.quicktransfer.exceptions.ResourceNotFoundException;
import com.example.quicktransfer.mapper.CustomerMapper;
import com.example.quicktransfer.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	@Override
	public CustomerResponse createCustomer(CreateCustomerRequest request) {
		log.info("Creating customer. phoneNumber={}", request.getPhoneNumber());
		Customer customer = customerMapper.toEntity(request);

		customer.setCreatedAt(LocalDateTime.now());
		customer.setUpdatedAt(LocalDateTime.now());
		customer.setActiveFlag(true);

		Customer savedCustomer = customerRepository.save(customer);
		log.info("Customer created successfully. customerId={}", savedCustomer.getCustomerId());
		return customerMapper.toResponse(savedCustomer);
	}

	@Override
	public CustomerResponse getCustomerById(Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id : " + customerId));

		return customerMapper.toResponse(customer);
	}
}
