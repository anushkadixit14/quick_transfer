package com.example.quicktransfer.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class CreateCustomerRequest {

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@NotBlank(message = "Phone number is required")
	private String phoneNumber;

	@Email(message = "Invalid email format")
	private String email;

	@NotNull(message = "Date of birth is required")
	private LocalDate dateOfBirth;
	
}
