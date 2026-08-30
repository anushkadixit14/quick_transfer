package com.example.quicktransfer.service;

import com.example.quicktransfer.dto.CreateCustomerRequest;
import com.example.quicktransfer.dto.CustomerResponse;

public interface CustomerService {
	
	CustomerResponse createCustomer(CreateCustomerRequest customerRequest);
	CustomerResponse getCustomerById(Long customerId);
}
