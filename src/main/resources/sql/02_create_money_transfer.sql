CREATE TABLE MONEY_TRANSFER (

    transaction_id BIGSERIAL PRIMARY KEY,

    reference_number VARCHAR(30) NOT NULL UNIQUE,

    customer_id BIGINT NOT NULL,

    receiver_name VARCHAR(100) NOT NULL,

    destination_country VARCHAR(50) NOT NULL,

    transfer_amount DECIMAL(15,2) NOT NULL,

    currency VARCHAR(3) NOT NULL,

    transfer_status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transfer_customer
        FOREIGN KEY (customer_id)
        REFERENCES CUSTOMER(customer_id)
);