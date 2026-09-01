ALTER TABLE money_transfer
ADD COLUMN store_id VARCHAR(20) NOT NULL;

ALTER TABLE money_transfer
ADD COLUMN register_id VARCHAR(20) NOT NULL;

ALTER TABLE money_transfer
ADD COLUMN operator_id VARCHAR(20) NOT NULL;

ALTER TABLE money_transfer
ADD COLUMN rquid VARCHAR(100) NOT NULL;
