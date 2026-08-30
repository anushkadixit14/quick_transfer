QuickTransfer
A Spring Boot based money transfer application that enables customers to transfer funds securely between accounts while enforcing business validations such as customer status, account existence, and transaction eligibility.

Project Description
QuickTransfer is a REST-based money transfer application developed using Spring Boot and PostgreSQL.

The application allows customers to send money to other customers while performing validations to ensure that transfers are secure and reliable. It also generates unique reference numbers for each transaction, making it easier to track and audit transfers.

Technologies Used
Java 17
Spring Boot 3.x
Spring Data JPA
Hibernate
PostgreSQL
Maven
REST APIs
Eclipse IDE
Git & GitHub
Features
Customer Management
Create customer records
Fetch customer information
Validate customer existence
Validate active/inactive customer status
Money Transfer
Transfer money between customers
Generate unique transaction/reference numbers
Store transfer history
Return transaction details
Business Validations
Sender must exist
Receiver must exist
Sender must be active
Receiver must be active
Transfer amount must be greater than zero
Prevent invalid transactions
Project Structure
quicktransfer
│
├── src/main/java
│   └── com.example.quicktransfer
│       ├── controller
│       ├── dto
│       ├── entity
│       ├── enums
│       ├── exception
│       ├── mapper
│       ├── repository
│       ├── service
│       └── service.impl
│
├── src/main/resources
│   ├── db
│   │   ├── 01_create_customer.sql
│   │   ├── 02_create_money_transfer.sql
│   │   └── 03_insert_test_data.sql
│   │
│   └── application.properties
│
├── pom.xml
└── README.md
Requirements
Before running the application, ensure the following are installed:

Java 17 or later
Maven 3.x
PostgreSQL 15+
Git
Database Setup
Before running the application, create a PostgreSQL database named quicktransfer.

CREATE DATABASE quicktransfer;
Connect to the database and execute the following scripts.

Customer Table
The customer table stores customer information and status details.

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
Column Description
customer_id – Unique customer identifier.
first_name – Customer first name.
last_name – Customer last name.
phone_number – Customer contact number.
email – Customer email address.
date_of_birth – Customer date of birth.
created_at – Record creation timestamp.
updated_at – Record modification timestamp.
active_flag – Indicates whether the customer is active.
Money Transfer Table
The money_transfer table stores transaction details and references the customer table through a foreign key.

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
Column Description
transaction_id – Unique transaction identifier.
reference_number – Unique transfer reference number.
customer_id – Customer who initiated the transfer.
receiver_name – Recipient of the transfer.
destination_country – Country where money is being sent.
transfer_amount – Amount being transferred.
currency – Transfer currency (USD, INR, EUR, etc.).
transfer_status – Transfer status (PENDING, SUCCESS, FAILED).
created_at – Transaction creation timestamp.
updated_at – Transaction modification timestamp.
Relationship
customer
    │
    │ customer_id
    ▼
money_transfer
One customer can create multiple money transfer transactions.

Application Configuration
Update the PostgreSQL credentials in:

src/main/resources/application.properties

spring.application.name=quicktransfer
spring.datasource.url=jdbc:postgresql://localhost:5432/quicktransfer
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
Note
spring.jpa.hibernate.ddl-auto=validate

Validates the database schema against JPA entities.
Does not create tables automatically.
Tables must already exist in PostgreSQL before starting the application. ``
How to Run the Project
Clone Repository
git clone https://github.com/Anju023/quick_transfer.git
Navigate to Project
cd quicktransfer
Build Project
mvn clean install
Run Application
mvn spring-boot:run
Application will start at:

http://localhost:8080
Application Flow
Create Customer
       │
       ▼
Validate Customer
       │
       ▼
Create Transfer Request
       │
       ▼
Validate Sender
       │
       ▼
Validate Receiver
       │
       ▼
Generate Reference Number
       │
       ▼
Save Transaction
       │
       ▼
Return Success Response
API Endpoints
Create Customer
POST

/api/customers
Request Body

{
  "firstName": "John",
  "lastName": "Smith",
  "phoneNumber": "9876543210",
  "email": "john@test.com",
  "dateOfBirth": "1995-05-20"
}
Sample Response

{
  "active": true,
  "customerId": 5,
  "dateOfBirth": "1995-05-20",
  "email": "john@test.com",
  "firstName": "John",
  "lastName": "Smith",
  "phoneNumber": "9876543210"
}
Create Money Transfer
POST

/api/transfers
Request Body

{
  "customerId": 1,
  "receiverName": "Alex Johnson",
  "destinationCountry": "CANADA",
  "transferAmount": 500,
  "currency": "CAD"
}
Sample Response

{
  "createdAt": "2026-08-24T14:22:04.757756",
  "currency": "CAD",
  "customerId": 1,
  "destinationCountry": "CANADA",
  "receiverName": "Alex Johnson",
  "referenceNumber": "QT20260824D215",
  "status": "CREATED",
  "transactionId": 5,
  "transferAmount": 500
}
Get Customer
GET

/api/customers/{id}
Sample Response

{
  "active": true,
  "customerId": 1,
  "dateOfBirth": "1995-05-20",
  "email": "john@test.com",
  "firstName": "John",
  "lastName": "Smith",
  "phoneNumber": "9876543210"
}
Reference Number Generation
Each transfer is assigned a unique reference number.

Example:

QT20260824D215
