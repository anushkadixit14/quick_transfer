CREATE TABLE transaction_status_history (

    history_id BIGSERIAL PRIMARY KEY,

    transaction_id BIGINT NOT NULL,

    old_status VARCHAR(20),

    new_status VARCHAR(20) NOT NULL,

    store_id VARCHAR(20) NOT NULL,

    register_id VARCHAR(20) NOT NULL,

    operator_id VARCHAR(20) NOT NULL,

    rquid VARCHAR(100) NOT NULL,

    changed_at TIMESTAMP NOT NULL,

    remarks VARCHAR(255),

    CONSTRAINT fk_transaction_status_history_transfer
        FOREIGN KEY (transaction_id)
        REFERENCES money_transfer(transaction_id)
);