package com.example.quicktransfer.mapper;

import org.springframework.stereotype.Component;

import com.example.quicktransfer.dto.CreateCustomerRequest;
import com.example.quicktransfer.dto.CustomerResponse;
import com.example.quicktransfer.entity.Customer;

@Component
public class CustomerMapper {
	public Customer toEntity(CreateCustomerRequest createCustomerRequest) {
		Customer customer = new Customer();
		customer.setFirstName(createCustomerRequest.getFirstName());
		customer.setLastName(createCustomerRequest.getLastName());
		customer.setPhoneNumber(createCustomerRequest.getPhoneNumber());
		customer.setDateOfBirth(createCustomerRequest.getDateOfBirth());
		customer.setEmail(createCustomerRequest.getEmail());
		
		return customer;
	}
	
	public CustomerResponse toResponse(Customer customer) {
		return CustomerResponse.builder()
				.customerId(customer.getCustomerId())
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.dateOfBirth(customer.getDateOfBirth())
				.email(customer.getEmail())
				.phoneNumber(customer.getPhoneNumber())
				.active(customer.getActiveFlag())
				.build();
	}
}
