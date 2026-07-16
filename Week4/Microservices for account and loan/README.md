# Microservices for Account and Loan

This project contains two Spring Boot microservices that handle account and loan management for a financial application.

## Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│           API Gateway / Client Application              │
└─────────────────────────────────────────────────────────┘
                          ↓
         ┌────────────────┴────────────────┐
         ↓                                 ↓
    ┌──────────────┐            ┌──────────────────┐
    │ Account      │            │ Loan Service     │
    │ Service      │            │ (Port 8082)      │
    │ (Port 8081)  │            └──────────────────┘
    └──────────────┘
         ↓                                 ↓
    ┌──────────────┐            ┌──────────────────┐
    │ H2 Database  │            │ H2 Database      │
    │ (Accounts)   │            │ (Loans)          │
    └──────────────┘            └──────────────────┘
```

## Services

### 1. Account Service (Port 8081)

**Purpose:** Manages customer accounts including account creation, updates, and retrieval.

**Database Tables:**
- `accounts` - Stores customer account information

**Entity Fields:**
- `accountId` - Primary key
- `accountNumber` - Unique account number
- `customerName` - Name of account holder
- `emailAddress` - Email for notifications
- `phoneNumber` - Contact number
- `balance` - Current account balance
- `accountType` - Type (Savings, Checking, etc.)
- `accountStatus` - Status (ACTIVE, CLOSED, etc.)
- `createdAt` - Creation timestamp
- `updatedAt` - Last update timestamp

### 2. Loan Service (Port 8082)

**Purpose:** Manages customer loans including loan applications, approvals, and EMI calculations.

**Database Tables:**
- `loans` - Stores loan information

**Entity Fields:**
- `loanId` - Primary key
- `loanNumber` - Unique loan number
- `accountId` - Reference to account
- `loanAmount` - Principal amount
- `interestRate` - Annual interest rate (%)
- `loanTenureMonths` - Loan duration in months
- `loanType` - Type (Personal, Home, Auto, etc.)
- `loanStatus` - Status (ACTIVE, CLOSED, DEFAULTED)
- `monthlyEMI` - Calculated monthly payment
- `startDate` - Loan start date
- `endDate` - Loan maturity date
- `createdAt` - Creation timestamp
- `updatedAt` - Last update timestamp

## API Endpoints

### Account Service APIs

#### Create Account
```
POST http://localhost:8081/api/accounts/create
Content-Type: application/json

{
  "accountNumber": "ACC001",
  "customerName": "John Doe",
  "emailAddress": "john@example.com",
  "phoneNumber": "9876543210",
  "balance": 50000,
  "accountType": "Savings"
}
```

#### Get All Accounts
```
GET http://localhost:8081/api/accounts/all
```

#### Get Account by ID
```
GET http://localhost:8081/api/accounts/{accountId}
```

#### Get Account by Number
```
GET http://localhost:8081/api/accounts/number/{accountNumber}
```

#### Get Accounts by Customer Name
```
GET http://localhost:8081/api/accounts/customer/{customerName}
```

#### Get Active Accounts
```
GET http://localhost:8081/api/accounts/active
```

#### Update Account
```
PUT http://localhost:8081/api/accounts/{accountId}
Content-Type: application/json

{
  "customerName": "John Doe Updated",
  "emailAddress": "john.updated@example.com",
  "balance": 75000,
  "accountStatus": "ACTIVE"
}
```

#### Delete Account
```
DELETE http://localhost:8081/api/accounts/{accountId}
```

### Loan Service APIs

#### Create Loan
```
POST http://localhost:8082/api/loans/create
Content-Type: application/json

{
  "loanNumber": "LOAN001",
  "accountId": 1,
  "loanAmount": 500000,
  "interestRate": 8.5,
  "loanTenureMonths": 60,
  "loanType": "Home"
}
```

#### Get All Loans
```
GET http://localhost:8082/api/loans/all
```

#### Get Loan by ID
```
GET http://localhost:8082/api/loans/{loanId}
```

#### Get Loan by Number
```
GET http://localhost:8082/api/loans/number/{loanNumber}
```

#### Get Loans by Account ID
```
GET http://localhost:8082/api/loans/account/{accountId}
```

#### Get Active Loans
```
GET http://localhost:8082/api/loans/active
```

#### Get Loans by Type
```
GET http://localhost:8082/api/loans/type/{loanType}
```

#### Update Loan
```
PUT http://localhost:8082/api/loans/{loanId}
Content-Type: application/json

{
  "loanAmount": 500000,
  "interestRate": 8.5,
  "loanTenureMonths": 60,
  "loanStatus": "ACTIVE"
}
```

#### Delete Loan
```
DELETE http://localhost:8082/api/loans/{loanId}
```

## Building and Running

### Build Both Services

```bash
# Account Service
cd account-service
mvn clean install

# Loan Service
cd ../loan-service
mvn clean install
```

### Run Services

```bash
# Terminal 1 - Account Service
cd account-service
mvn spring-boot:run

# Terminal 2 - Loan Service
cd loan-service
mvn spring-boot:run
```

## Database Access

Both services use H2 in-memory databases with web consoles available at:

- **Account Service Console:** http://localhost:8081/h2-console
- **Loan Service Console:** http://localhost:8082/h2-console

JDBC URLs:
- Account Service: `jdbc:h2:mem:accountdb`
- Loan Service: `jdbc:h2:mem:loandb`

Username: `sa`
Password: (leave empty)

## Technologies Used

- **Java 17+**
- **Spring Boot 3.1.5**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database**
- **Lombok**
- **Maven**

## Project Structure

```
microservices-account-loan/
├── account-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/microservice/account/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   ├── entity/
│   │   │   │   └── AccountServiceApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
└── loan-service/
    ├── src/
    │   ├── main/
    │   │   ├── java/com/microservice/loan/
    │   │   │   ├── controller/
    │   │   │   ├── service/
    │   │   │   ├── repository/
    │   │   │   ├── entity/
    │   │   │   └── LoanServiceApplication.java
    │   │   └── resources/
    │   │       └── application.properties
    │   └── test/
    └── pom.xml
```

## Key Features

### Account Service
- ✅ Create, read, update, delete accounts
- ✅ Search accounts by number or customer name
- ✅ Track account status and balance
- ✅ Automatic timestamp management

### Loan Service
- ✅ Create, read, update, delete loans
- ✅ Automatic EMI (Equated Monthly Installment) calculation
- ✅ Search loans by account, type, or status
- ✅ Track loan lifecycle (start date, end date, status)

## Future Enhancements

1. **Service-to-Service Communication:** Implement inter-service calls using RestTemplate or Feign
2. **API Gateway:** Add Spring Cloud Gateway for unified API access
3. **Service Discovery:** Implement Eureka for service registration
4. **Configuration Server:** Add Spring Cloud Config
5. **Security:** Implement JWT authentication and authorization
6. **Logging & Monitoring:** Add ELK stack and Prometheus
7. **Circuit Breaker:** Implement Hystrix for fault tolerance
8. **Unit & Integration Tests:** Add comprehensive test coverage
9. **Docker:** Containerize services with Docker
10. **Kubernetes:** Deploy on Kubernetes for orchestration

## Notes

- Each service maintains its own database (Database per service pattern)
- Services are completely independent and can be scaled separately
- Current implementation uses H2 in-memory database (suitable for development/testing)
- For production, replace H2 with PostgreSQL/MySQL and configure persistent storage
