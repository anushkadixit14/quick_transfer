# QuickTransfer V2.0.0

QuickTransfer is a Spring Boot and PostgreSQL based money transfer backend application designed to simulate enterprise-style transaction processing.

Version 2.0.0 extends the functionality introduced in V1.0.0 by adding POS transaction context, RQUID correlation tracking, transaction status history, advanced search capabilities, pagination, filtering, sorting, and transaction-level logging.

The application follows a layered architecture using Controllers, Services, DTOs, Mappers, Repositories, and PostgreSQL persistence, ensuring clean separation of concerns, scalability, and maintainability.

---

# Version Information

| Version | Description | Git Tag |
|-----------|-------------|----------|
| V1.0.0 | Customer Management, Money Transfer & Status Management | v1.0.0 |
| V2.0.0 | Transaction Context, History, Search, Pagination & Logging | v2.0.0 |

---

# Documentation

Version specific documentation:

- docs/QUICKTRANSFER_V1.md

Current Branch Version:

```text
QuickTransfer V2.0.0
```

---

# Features

## Customer Management

- Create Customer
- Retrieve Customer by ID
- Active Customer Validation
- Customer Existence Validation

## Money Transfer

- Create Transfer
- Retrieve Transfer by Transaction ID
- Retrieve Transfer by Reference Number
- Unique Reference Number Generation

## POS Transaction Context

Every transaction captures:

- Store ID
- Register ID
- Operator ID
- RQUID

Context is supplied through HTTP headers:

```http
X-Store-Id
X-Register-Id
X-Operator-Id
X-Rquid
```

## Transaction Status Management

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

## Transaction Status History

Every transaction event is recorded in:

```text
TRANSACTION_STATUS_HISTORY
```

History captures:

- Previous Status
- New Status
- Store ID
- Register ID
- Operator ID
- RQUID
- Change Timestamp
- Remarks

## Transaction Search

Search transfers using:

- Reference Number
- Customer ID
- Status
- Store ID
- Register ID
- Destination Country
- From Date
- To Date

## Pagination & Sorting

Supports:

```text
page
size
```

Maximum Page Size:

```text
50
```

Default Sorting:

```text
createdAt DESC
```

## Transaction Logging

Transaction logs include:

- Transaction ID
- Reference Number
- RQUID
- Store ID
- Register ID
- Operator ID
- Transaction Status

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
- Bruno
- Git
- GitHub

---

# High-Level Architecture

```text
REST Client
(Postman / Bruno)
         |
         |
 Headers + Body
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
|    Mapper      |
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

# Database Relationships

```text
CUSTOMER
    |
    | 1
    |
    | *
    v
MONEY_TRANSFER
    |
    | 1
    |
    | *
    v
TRANSACTION_STATUS_HISTORY
```

---

# Project Structure

```text
quicktransfer
│
├── src/main/java/com/example/quicktransfer
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── enums
│   ├── exceptions
│   ├── mapper
│   ├── repository
│   ├── service
│   └── specification
│
├── src/main/resources/sql
│   ├── 01_create_customer.sql
│   ├── 02_create_money_transfer.sql
│   ├── 03_insert_test_data.sql
│   ├── 04_add_transaction_context.sql
│   └── 05_create_transaction_status_history.sql
│
├── Bruno
│   └── Collections
│       ├── QuickTransfer V1.json
│       └── QuickTransfer V2.json
│
├── docs
│   └── QUICKTRANSFER_V1.md
│
├── README.md
├── pom.xml
├── mvnw
└── mvnw.cmd
```

---

# Prerequisites

Ensure the following software is installed:

- Java 17+
- Maven 3.x+
- PostgreSQL 15+
- Git
- Eclipse / STS

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

# Clone Repository

```bash
git clone https://github.com/anushkadixit14/quick_transfer.git
```

Navigate to project:

```bash
cd quick_transfer
```

---

# Database Setup

## Step 1 - Create Database

```sql
CREATE DATABASE quicktransfer;
```

Connect to database:

```sql
\c quicktransfer
```

---

## Step 2 - Execute SQL Scripts

Run scripts in the following order:

```text
01_create_customer.sql

02_create_money_transfer.sql

03_insert_test_data.sql

04_add_transaction_context.sql

05_create_transaction_status_history.sql
```

---

# Database Tables

## CUSTOMER

Stores customer information.

Key fields:

```text
customer_id
first_name
last_name
phone_number
email
date_of_birth
active_flag
```

---

## MONEY_TRANSFER

Stores money transfer transactions.

Key fields:

```text
transaction_id
reference_number
customer_id
store_id
register_id
operator_id
rquid
receiver_name
destination_country
transfer_amount
currency
transfer_status
```

---

## TRANSACTION_STATUS_HISTORY

Stores transaction lifecycle events.

Key fields:

```text
history_id
transaction_id
old_status
new_status
store_id
register_id
operator_id
rquid
changed_at
remarks
```

---

# Application Configuration

Update:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.application.name=quicktransfer

server.port=8095

spring.datasource.url=jdbc:postgresql://localhost:5432/quicktransfer
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=validate

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

# Build Project

```bash
mvn clean install
```

Successful 
