package com.example.quicktransfer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

	private LocalDateTime timestamp;

	private Integer status;

	private String code;

	private String message;
}