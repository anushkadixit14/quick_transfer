# QuickTransfer

A Spring Boot based money transfer application that enables customers to transfer funds securely while enforcing business validations such as customer existence, customer status, transfer eligibility, and transaction status transitions.

QuickTransfer follows a layered architecture using Controllers, Services, DTOs, Mappers, Repositories, and PostgreSQL persistence to provide a clean, scalable, and maintainable REST API solution. The application generates unique business reference numbers for every transaction and supports complete transaction lifecycle management.

---

# Table of Contents

- #project-description
- #technologies-used
- #features
  - #customer-management
  - #money-transfer
  - #transfer-status-management
  - #business-validations
- #high-level-architecture
- #project-structure
- #requirements
- #installation-guide
- #database-setup
  - #create-database
  - #create-customer-table
  - #create-money-transfer-table
  - #insert-sample-customer-data
- #database-relationship
- #application-configuration
- #import-project-into-sts
- #running-the-application
- #application-flow
- #api-endpoints
  - #customer-apis
  - #transfer-apis
- #reference-number-generation
- #exception-handling
- #testing
- #future-enhancements
- #author
- #license

---

# Project Description

QuickTransfer is a RESTful backend application developed using Spring Boot and PostgreSQL.

The application allows:

- Customer creation and retrieval
- Money transfer creation
- Transaction retrieval
- Transaction status updates
- Search using reference number
- Business validation handling
- Global exception handling
- PostgreSQL-based persistence

The project is designed to simulate a simplified money transfer platform while maintaining enterprise-style backend architecture.

---

# Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok
- Jakarta Bean Validation
- JUnit 5
- REST APIs
- Eclipse / STS
- Git & GitHub
- Bruno API Testing

---

# Features

## Customer Management

- Create customer
- Retrieve customer by ID
- Validate customer existence
- Validate active/inactive customer status

## Money Transfer

- Create money transfer transactions
- Retrieve transactions
- Search transactions using reference numbers
- Generate unique reference numbers
- Maintain transaction history

## Transfer Status Management

Supported statuses:

```text
CREATED
VALIDATED
COMPLETED
FAILED
```

Allowed transitions:

```text
CREATED → VALIDATED
VALIDATED → COMPLETED

CREATED → FAILED
VALIDATED → FAILED
```

Invalid status transitions are rejected.

## Business Validations

### Customer

- Customer must exist
- Customer must be active

### Transfer

- Transfer amount must be greater than zero
- Receiver name cannot be blank
- Destination country cannot be blank
- Currency must contain exactly 3 characters

### Status

- Invalid status transitions are prevented
- Proper business error responses are returned

---

# High-Level Architecture

```text
REST Client
(Postman / Bruno)
        |
        v
+----------------+
|   Controller   |
+----------------+
        |
        v
+----------------+
|    Service     |
| Business Rules |
+----------------+
        |
        v
+----------------+
|     Mapper     |
+----------------+
        |
        v
+----------------+
|   Repository   |
+----------------+
        |
        v
+----------------+
|   PostgreSQL   |
+----------------+
```

---

# Project Structure

```text
quicktransfer
│
├── src/main/java
│   └── com.example.quicktransfer
│       ├── controller
│       ├── dto
│       ├── entity
│       ├── enums
│       ├── exceptions
│       ├── mapper
│       ├── repository
│       ├── service
│
├── src/main/resources
│   └── application.properties
│
├── QuickTransfer V1.json
├── pom.xml
├── README.md
├── mvnw
└── mvnw.cmd
```

---

# Requirements

Before running the application, ensure the following are installed:

- Java 17+
- Maven 3.x
- PostgreSQL 15+
- Git
- Eclipse / STS
- Bruno or Postman

Verify installation:

```bash
java --version
```

```bash
mvn --version
```

```bash
psql --version
```

---

# Installation Guide

## Clone Repository

```bash
git clone https://github.com/anushkadixit14/quick_transfer.git
```

## Navigate to Project

```bash
cd quick_transfer
```

## Build Project

```bash
mvn clean install
```

---

# Database Setup

## Create Database

```sql
CREATE DATABASE quicktransfer;
```

Connect to database:

```sql
\c quicktransfer
```

---

## Create Customer Table

```sql
CREATE TABLE customer (
    customer_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    date_of_birth DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active_flag BOOLEAN DEFAULT TRUE
);
```

---

## Create Money Transfer Table

```sql
CREATE TABLE money_transfer (
    transaction_id BIGSERIAL PRIMARY KEY,
    reference_number VARCHAR(30) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL,
    receiver_name VARCHAR(100) NOT NULL,
    destination_country VARCHAR(50) NOT NULL,
    transfer_amount DECIMAL(15,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    transfer_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_customer
    FOREIGN KEY(customer_id)
    REFERENCES customer(customer_id)
);
```

---

## Insert Sample Customer Data

### Active Customers

```sql
INSERT INTO customer
(first_name,last_name,phone_number,email,date_of_birth,active_flag)
VALUES
('John','Smith','9876543210','john@test.com','1995-05-20',TRUE),
('Alex','Johnson','9876543211','alex@test.com','1992-10-18',TRUE);
```

### Inactive Customer

```sql
INSERT INTO customer
(first_name,last_name,phone_number,email,date_of_birth,active_flag)
VALUES
('Inactive','Customer','9876543212',
'inactive@test.com','1990-01-01',FALSE);
```

---

# Database Relationship

```text
CUSTOMER
    |
    | 1
    |
    | *
    v
MONEY_TRANSFER
```

One customer can create multiple money transfer transactions.

---

# Application Configuration

Update PostgreSQL credentials in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.application.name=quicktransfer

spring.datasource.url=jdbc:postgresql://localhost:5432/quicktransfer
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Important

```properties
spring.jpa.hibernate.ddl-auto=validate
```

This configuration:

- Validates schema against JPA entities
- Does not create tables automatically
- Ensures database tables already exist

---

# Import Project into STS

```text
File
 -> Import
     -> Existing Maven Project
```

Choose:

```text
quick_transfer
```

Allow Maven dependencies to download.

---

# Running the Application

## Using Maven

```bash
mvn spring-boot:run
```

## Using STS

Run:

```text
QuicktransferApplication.java
```

Application starts at:

```text
http://localhost:8095
```

Expected Startup Log:

```text
Started QuicktransferApplication
```

---

# Application Flow

```text
Create Customer
       |
       v
Validate Customer
       |
       v
Create Transfer
       |
       v
Validate Sender
       |
       v
Validate Transfer Details
       |
       v
Generate Reference Number
       |
       v
Save Transaction
       |
       v
Return Success Response
```

---

# API Endpoints

# Customer APIs

## Create Customer

**POST**

```http
/api/v1/customers
```

Request

```json
{
  "firstName": "John",
  "lastName": "Smith",
  "phoneNumber": "9876543210",
  "email": "john@test.com",
  "dateOfBirth": "1995-05-20"
}
```

Response

```json
{
  "customerId": 1001,
  "firstName": "John",
  "lastName": "Smith",
  "phoneNumber": "9876543210",
  "email": "john@test.com",
  "dateOfBirth": "1995-05-20",
  "active": true
}
```

**Status:** `201 CREATED`

---

## Get Customer

**GET**

```http
/api/v1/customers/{customerId}
```

Example:

```http
/api/v1/customers/1001
```

**Status Codes**

```text
200 OK
404 NOT FOUND
```

---

# Transfer APIs

## Create Transfer

**POST**

```http
/api/v1/transfers
```

Request

```json
{
  "customerId": 1,
  "receiverName": "Alex Johnson",
  "destinationCountry": "CANADA",
  "transferAmount": 500,
  "currency": "CAD"
}
```

Response

```json
{
  "transactionId": 2001,
  "referenceNumber": "QT202608180001",
  "customerId": 1,
  "receiverName": "Alex Johnson",
  "destinationCountry": "CANADA",
  "transferAmount": 500,
  "currency": "CAD",
  "status": "CREATED"
}
```

**Status:** `201 CREATED`

---

## Get Transfer

**GET**

```http
/api/v1/transfers/{transactionId}
```

**Status Codes**

```text
200 OK
404 NOT FOUND
```

---

## Search Transfer By Reference Number

**GET**

```http
/api/v1/transfers/reference/{referenceNumber}
```

Example:

```http
/api/v1/transfers/reference/QT202608180001
```

---

## Update Transfer Status

**PATCH**

```http
/api/v1/transfers/{transactionId}/status
```

Request

```json
{
  "status": "VALIDATED"
}
```

Example Flow:

```text
CREATED → VALIDATED
VALIDATED → COMPLETED
```

---

# Reference Number Generation

Every transfer receives a unique business reference number.

Example:

```text
QT202608180001
```

Characteristics:

- Unique
- System Generated
- Stored in Database
- Returned in API Responses
- Used for transaction tracking

---

# Exception Handling

Global exception handling is implemented using:

```java
@RestControllerAdvice
```

Handled Exceptions:

- ResourceNotFoundException
- BusinessValidationException
- InvalidStatusTransitionException
- MethodArgumentNotValidException

Sample Error Response:

```json
{
  "timestamp": "2026-08-18T10:30:00",
  "status": 400,
  "code": "VALIDATION_ERROR",
  "message": "Transfer amount must be greater than zero"
}
```

---

# Testing

## Bruno API Collection

Included in repository:

```text
QuickTransfer V1.json
```

Import into Bruno or Postman.

---

## Customer Test Scenarios

- Create valid customer
- Create invalid customer
- Get existing customer
- Get non-existing customer

---

## Transfer Test Scenarios

- Create valid transfer
- Create transfer for non-existing customer
- Create transfer for inactive customer
- Create transfer with zero amount
- Create transfer with negative amount
- Create transfer without receiver
- Get existing transfer
- Get non-existing transfer
- Search using reference number

---

## Status Transition Scenarios

- CREATED → VALIDATED
- VALIDATED → COMPLETED
- CREATED → FAILED
- VALIDATED → FAILED
- Invalid transition from COMPLETED
- Invalid transition from FAILED

---

# Future Enhancements

- JWT Authentication
- Role Based Authorization
- Refund Transactions
- Receive Flow
- Email Notifications
- SMS Notifications
- Docker Support
- Kafka Integration
- Microservices Architecture
- External Payment Integration

---

# Author

**Anushka Dixit**

Graduate Engineer Trainee

### Skills & Technologies

- Java
- Spring Boot
- Hibernate
- Spring Data JPA
- PostgreSQL
- REST APIs
- Maven
- Git
- GitHub
- Bruno API Testing

### Repository

https://github.com/anushkadixit14/quick_transfer

---

# License

This project is intended for educational, learning, and demonstration purposes.
