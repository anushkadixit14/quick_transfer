package com.example.quicktransfer.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private Long customerId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private LocalDate dateOfBirth;

    private Boolean active;
}
