# Quick Setup Guide

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git
- A terminal/command prompt

## Verify Prerequisites

```bash
java -version
mvn -version
```

## Building the Project

### 1. Navigate to account-service directory
```bash
cd account-service
mvn clean install
```

### 2. Navigate to loan-service directory
```bash
cd ../loan-service
mvn clean install
```

## Running the Services

### Method 1: Using Maven

#### Terminal 1 - Start Account Service
```bash
cd account-service
mvn spring-boot:run
```

#### Terminal 2 - Start Loan Service
```bash
cd loan-service
mvn spring-boot:run
```

### Method 2: Using JAR Files

#### After building, run:
```bash
# Account Service
java -jar account-service/target/account-service-1.0.0.jar

# Loan Service (in another terminal)
java -jar loan-service/target/loan-service-1.0.0.jar
```

## Verify Services Are Running

### Account Service Health Check
```bash
curl http://localhost:8081/api/accounts/all
```

Expected Response: `[]` (empty JSON array)

### Loan Service Health Check
```bash
curl http://localhost:8082/api/loans/all
```

Expected Response: `[]` (empty JSON array)

## Test with Sample Data

### 1. Create a Sample Account
```bash
curl -X POST http://localhost:8081/api/accounts/create \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "ACC001",
    "customerName": "John Doe",
    "emailAddress": "john@example.com",
    "phoneNumber": "9876543210",
    "balance": 50000,
    "accountType": "Savings"
  }'
```

Save the `accountId` from the response (e.g., `"accountId": 1`)

### 2. Create a Sample Loan
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

### 3. Retrieve the Account
```bash
curl http://localhost:8081/api/accounts/1
```

### 4. Retrieve the Loan
```bash
curl http://localhost:8082/api/loans/1
```

## Access H2 Database Consoles

### Account Service Console
- URL: http://localhost:8081/h2-console
- JDBC URL: `jdbc:h2:mem:accountdb`
- Username: `sa`
- Password: (leave empty)

### Loan Service Console
- URL: http://localhost:8082/h2-console
- JDBC URL: `jdbc:h2:mem:loandb`
- Username: `sa`
- Password: (leave empty)

## Common Commands

### View All Accounts
```bash
curl http://localhost:8081/api/accounts/all
```

### View All Loans
```bash
curl http://localhost:8082/api/loans/all
```

### View Loans for Specific Account
```bash
curl http://localhost:8082/api/loans/account/1
```

### Update Account
```bash
curl -X PUT http://localhost:8081/api/accounts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Jane Doe",
    "emailAddress": "jane@example.com",
    "balance": 75000,
    "accountStatus": "ACTIVE"
  }'
```

### Delete Account
```bash
curl -X DELETE http://localhost:8081/api/accounts/1
```

## Stopping Services

Press `Ctrl+C` in the terminal where the service is running

## Troubleshooting

### Port 8081 already in use
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F

# Mac/Linux
lsof -i :8081
kill -9 <PID>
```

### Maven build fails
```bash
# Clear Maven cache
mvn clean

# Rebuild with updates
mvn clean install -U
```

### Can't connect to H2 console
- Verify the service is running (check logs)
- Check that the JDBC URL matches the application.properties file
- Ensure port 8081 or 8082 is accessible

## Next Steps

1. Read the **README.md** for detailed architecture overview
2. Check **API_TESTING_GUIDE.md** for comprehensive API examples
3. Review **IMPLEMENTATION_SUMMARY.md** for roadmap and enhancements
4. Run tests: `mvn test` in each service directory
5. Explore the code in `src/main/java` to understand the implementation

## Project Documentation

- **README.md** - Architecture, features, and detailed documentation
- **API_TESTING_GUIDE.md** - Complete API testing examples with cURL
- **IMPLEMENTATION_SUMMARY.md** - Summary, roadmap, and next steps
- **QUICK_SETUP_GUIDE.md** - This file

---

**Estimated Setup Time**: 5-10 minutes
**Estimated First Test**: 2 minutes after starting services
