package com.example.quicktransfer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.quicktransfer.dto.CreateCustomerRequest;
import com.example.quicktransfer.dto.CustomerResponse;
import com.example.quicktransfer.entity.Customer;
import com.example.quicktransfer.exceptions.ResourceNotFoundException;
import com.example.quicktransfer.mapper.CustomerMapper;
import com.example.quicktransfer.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerMapper customerMapper;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Test
	void createCustomer_ShouldReturnCustomerResponse() {

		CreateCustomerRequest request = new CreateCustomerRequest();

		request.setFirstName("John");
		request.setLastName("Smith");

		Customer customer = new Customer();

		Customer savedCustomer = new Customer();
		savedCustomer.setCustomerId(1L);

		CustomerResponse response = CustomerResponse.builder().customerId(1L).firstName("John").build();

		when(customerMapper.toEntity(request)).thenReturn(customer);

		when(customerRepository.save(customer)).thenReturn(savedCustomer);

		when(customerMapper.toResponse(savedCustomer)).thenReturn(response);

		CustomerResponse result = customerService.createCustomer(request);

		assertNotNull(result);
		assertEquals(1L, result.getCustomerId());
	}

	@Test
    void getCustomerById_ShouldThrowException_WhenCustomerNotFound() {

        when(customerRepository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getCustomerById(100L));
    }
}