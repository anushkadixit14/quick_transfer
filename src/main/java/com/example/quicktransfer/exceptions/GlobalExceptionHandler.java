package com.example.quicktransfer.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.quicktransfer.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception){
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.code("RESOURCE_NOT_FOUND")
				.message(exception.getMessage())
				.build();
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidStatusTransitionException.class)
	public ResponseEntity<ErrorResponse> handleStateTransition(InvalidStatusTransitionException exception){
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.code("INVALID STATE TRANSITION")
				.message(exception.getMessage())
				.build();
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(BussinessValidationException.class)
	public ResponseEntity<ErrorResponse> handleValidation(BussinessValidationException ex) {
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.code("VALIDATION_ERROR")
				.message(ex.getMessage())	
				.build();
		
		return new ResponseEntity<>(error, BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleBeanValidation(MethodArgumentNotValidException ex) {
		String message =ex.getBindingResult()
			.getFieldError()
			.getDefaultMessage();
		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
	            .status(HttpStatus.BAD_REQUEST.value())
	            .code("VALIDATION_ERROR")
	            .message(message)
	            .build();
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
