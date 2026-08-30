INSERT INTO CUSTOMER
(
 first_name,
 last_name,
 phone_number,
 email,
 date_of_birth,
 active_flag
)
VALUES
(
 'John',
 'Smith',
 '9876543210',
 'john.smith@example.com',
 '1995-05-20',
 TRUE
);

INSERT INTO CUSTOMER
(
 first_name,
 last_name,
 phone_number,
 email,
 date_of_birth,
 active_flag
)
VALUES
(
 'Emma',
 'Brown',
 '9876543211',
 'emma.brown@example.com',
 '1992-08-15',
 TRUE
);

INSERT INTO CUSTOMER
(
 first_name,
 last_name,
 phone_number,
 email,
 date_of_birth,
 active_flag
)
VALUES
(
 'Inactive',
 'Customer',
 '9876543212',
 'inactive@example.com',
 '1990-01-01',
 FALSE
);

INSERT INTO MONEY_TRANSFER
(
 reference_number,
 customer_id,
 receiver_name,
 destination_country,
 transfer_amount,
 currency,
 transfer_status
)
VALUES
(
 'QT202608180001',
 1,
 'Alex Johnson',
 'CANADA',
 500.00,
 'CAD',
 'CREATED'
);

INSERT INTO MONEY_TRANSFER
(
 reference_number,
 customer_id,
 receiver_name,
 destination_country,
 transfer_amount,
 currency,
 transfer_status
)
VALUES
(
 'QT202608180002',
 2,
 'David Miller',
 'USA',
 1000.00,
 'USD',
 'VALIDATED'
);