# API Testing Guide

This document provides examples for testing the Account and Loan microservices APIs using cURL commands or Postman.

## Testing Account Service

### 1. Create an Account

```bash
curl -X POST http://localhost:8081/api/accounts/create \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "ACC001",
    "customerName": "Rajesh Kumar",
    "emailAddress": "rajesh@example.com",
    "phoneNumber": "9876543210",
    "balance": 50000,
    "accountType": "Savings"
  }'
```

**Response:**
```json
{
  "accountId": 1,
  "accountNumber": "ACC001",
  "customerName": "Rajesh Kumar",
  "emailAddress": "rajesh@example.com",
  "phoneNumber": "9876543210",
  "balance": 50000,
  "accountType": "Savings",
  "accountStatus": "ACTIVE",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 2. Get All Accounts

```bash
curl -X GET http://localhost:8081/api/accounts/all
```

### 3. Get Account by ID

```bash
curl -X GET http://localhost:8081/api/accounts/1
```

### 4. Get Account by Account Number

```bash
curl -X GET http://localhost:8081/api/accounts/number/ACC001
```

### 5. Get Accounts by Customer Name

```bash
curl -X GET http://localhost:8081/api/accounts/customer/Rajesh%20Kumar
```

### 6. Get Active Accounts

```bash
curl -X GET http://localhost:8081/api/accounts/active
```

### 7. Update Account

```bash
curl -X PUT http://localhost:8081/api/accounts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Rajesh Kumar Updated",
    "emailAddress": "rajesh.updated@example.com",
    "phoneNumber": "9876543211",
    "balance": 75000,
    "accountStatus": "ACTIVE"
  }'
```

### 8. Delete Account

```bash
curl -X DELETE http://localhost:8081/api/accounts/1
```

---

## Testing Loan Service

### 1. Create a Loan

```bash
curl -X POST http://localhost:8082/api/loans/create \
  -H "Content-Type: application/json" \
  -d '{
    "loanNumber": "LOAN001",
    "accountId": 1,
    "loanAmount": 500000,
    "interestRate": 8.5,
    "loanTenureMonths": 60,
    "loanType": "Home"
  }'
```

**Response:**
```json
{
  "loanId": 1,
  "loanNumber": "LOAN001",
  "accountId": 1,
  "loanAmount": 500000,
  "interestRate": 8.5,
  "loanTenureMonths": 60,
  "loanType": "Home",
  "loanStatus": "ACTIVE",
  "monthlyEMI": 9952.80,
  "startDate": "2024-01-15",
  "endDate": "2029-01-15",
  "createdAt": "2024-01-15T10:35:00",
  "updatedAt": "2024-01-15T10:35:00"
}
```

### 2. Get All Loans

```bash
curl -X GET http://localhost:8082/api/loans/all
```

### 3. Get Loan by ID

```bash
curl -X GET http://localhost:8082/api/loans/1
```

### 4. Get Loan by Loan Number

```bash
curl -X GET http://localhost:8082/api/loans/number/LOAN001
```

### 5. Get Loans by Account ID

```bash
curl -X GET http://localhost:8082/api/loans/account/1
```

### 6. Get Active Loans

```bash
curl -X GET http://localhost:8082/api/loans/active
```

### 7. Get Loans by Type

```bash
curl -X GET http://localhost:8082/api/loans/type/Home
```

### 8. Update Loan

```bash
curl -X PUT http://localhost:8082/api/loans/1 \
  -H "Content-Type: application/json" \
  -d '{
    "loanAmount": 500000,
    "interestRate": 8.0,
    "loanTenureMonths": 60,
    "loanStatus": "ACTIVE"
  }'
```

### 9. Delete Loan

```bash
curl -X DELETE http://localhost:8082/api/loans/1
```

---

## Testing Flow Example

### Step 1: Create an Account
Create a customer account with initial balance:
```bash
curl -X POST http://localhost:8081/api/accounts/create \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "ACC002",
    "customerName": "Priya Singh",
    "emailAddress": "priya@example.com",
    "phoneNumber": "9876543220",
    "balance": 100000,
    "accountType": "Checking"
  }'
```

### Step 2: Get Account Details
```bash
curl -X GET http://localhost:8081/api/accounts/number/ACC002
```

### Step 3: Create a Loan for the Account
Use the accountId from Step 2:
```bash
curl -X POST http://localhost:8082/api/loans/create \
  -H "Content-Type: application/json" \
  -d '{
    "loanNumber": "LOAN002",
    "accountId": 2,
    "loanAmount": 300000,
    "interestRate": 7.5,
    "loanTenureMonths": 48,
    "loanType": "Personal"
  }'
```

### Step 4: Get Loan Details
```bash
curl -X GET http://localhost:8082/api/loans/number/LOAN002
```

### Step 5: Get All Loans for Account
```bash
curl -X GET http://localhost:8082/api/loans/account/2
```

---

## Common Loan Types

- **Home Loan**: Long-term loans for property purchase
- **Personal Loan**: Unsecured loans for personal use
- **Auto Loan**: Loans for vehicle purchase
- **Education Loan**: Loans for educational expenses
- **Business Loan**: Loans for business purposes

---

## Sample Data for Testing

### Sample Accounts
```json
[
  {
    "accountNumber": "ACC001",
    "customerName": "Rajesh Kumar",
    "emailAddress": "rajesh@example.com",
    "phoneNumber": "9876543210",
    "balance": 50000,
    "accountType": "Savings"
  },
  {
    "accountNumber": "ACC002",
    "customerName": "Priya Singh",
    "emailAddress": "priya@example.com",
    "phoneNumber": "9876543220",
    "balance": 100000,
    "accountType": "Checking"
  }
]
```

### Sample Loans
```json
[
  {
    "loanNumber": "LOAN001",
    "accountId": 1,
    "loanAmount": 500000,
    "interestRate": 8.5,
    "loanTenureMonths": 60,
    "loanType": "Home"
  },
  {
    "loanNumber": "LOAN002",
    "accountId": 2,
    "loanAmount": 300000,
    "interestRate": 7.5,
    "loanTenureMonths": 48,
    "loanType": "Personal"
  }
]
```

---

## Notes

- All timestamps are in ISO 8601 format
- Balance and amounts are in currency units (e.g., INR)
- EMI is automatically calculated when a loan is created
- Account status defaults to "ACTIVE" on creation
- Loan status defaults to "ACTIVE" on creation
- Both services use in-memory H2 databases
