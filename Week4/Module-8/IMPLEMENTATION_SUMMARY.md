# Project Summary: Microservices for Account and Loan

## Overview

A complete microservices-based application with two independent Spring Boot services for managing customer accounts and loans. The project follows industry best practices including the database-per-service pattern, RESTful APIs, and clean architecture.

## What Has Been Built

### ✅ Account Service
- **Port**: 8081
- **Purpose**: Manage customer accounts
- **Features**:
  - Create, read, update, delete accounts
  - Search by account number or customer name
  - Filter by status
  - Automatic audit fields (createdAt, updatedAt)
  - H2 in-memory database
  - Spring Data JPA with Hibernate ORM

**Endpoints**: 7 REST endpoints for account operations

### ✅ Loan Service
- **Port**: 8082
- **Purpose**: Manage customer loans
- **Features**:
  - Create, read, update, delete loans
  - Automatic EMI calculation (monthly payment)
  - Search by account, loan type, or status
  - Link loans to accounts
  - Automatic date management (start date, end date)
  - H2 in-memory database
  - Spring Data JPA with Hibernate ORM

**Endpoints**: 9 REST endpoints for loan operations

### ✅ Testing Infrastructure
- Unit tests for both services
- Integration tests using @SpringBootTest
- Service layer tests with repository mocking

### ✅ Documentation
- Comprehensive README with architecture overview
- API testing guide with cURL examples
- Database schema documentation
- Sample test data

## Project Structure

```
Microservices for account and loan/
├── README.md                          # Main documentation
├── API_TESTING_GUIDE.md              # Testing guide with examples
│
├── account-service/
│   ├── pom.xml                       # Maven configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/microservice/account/
│   │   │   │   ├── entity/Account.java
│   │   │   │   ├── repository/AccountRepository.java
│   │   │   │   ├── service/AccountService.java
│   │   │   │   ├── controller/AccountController.java
│   │   │   │   └── AccountServiceApplication.java
│   │   │   └── resources/application.properties
│   │   └── test/java/com/microservice/account/
│   │       └── AccountServiceTest.java
│
└── loan-service/
    ├── pom.xml                       # Maven configuration
    ├── src/
    │   ├── main/
    │   │   ├── java/com/microservice/loan/
    │   │   │   ├── entity/Loan.java
    │   │   │   ├── repository/LoanRepository.java
    │   │   │   ├── service/LoanService.java
    │   │   │   ├── controller/LoanController.java
    │   │   │   └── LoanServiceApplication.java
    │   │   └── resources/application.properties
    │   └── test/java/com/microservice/loan/
    │       └── LoanServiceTest.java
```

## Quick Start

### 1. Build Services
```bash
# Build Account Service
cd account-service
mvn clean install

# Build Loan Service
cd ../loan-service
mvn clean install
```

### 2. Run Services
```bash
# Terminal 1: Run Account Service
cd account-service
mvn spring-boot:run

# Terminal 2: Run Loan Service
cd loan-service
mvn spring-boot:run
```

### 3. Test Services
```bash
# Account Service
curl http://localhost:8081/api/accounts/all

# Loan Service
curl http://localhost:8082/api/loans/all
```

### 4. Access Database Consoles
- Account Service: http://localhost:8081/h2-console
- Loan Service: http://localhost:8082/h2-console

## Key Technologies

| Component | Version |
|-----------|---------|
| Java | 17+ |
| Spring Boot | 3.1.5 |
| Spring Data JPA | 3.1.5 |
| Hibernate | 6.2.x |
| H2 Database | Latest |
| Maven | 3.6+ |
| Lombok | Latest |
| JUnit 5 | Latest |

## API Summary

### Account Service (Port 8081)
- `POST /api/accounts/create` - Create account
- `GET /api/accounts/all` - Get all accounts
- `GET /api/accounts/{accountId}` - Get account by ID
- `GET /api/accounts/number/{accountNumber}` - Get by number
- `GET /api/accounts/customer/{customerName}` - Get by customer name
- `GET /api/accounts/active` - Get active accounts
- `PUT /api/accounts/{accountId}` - Update account
- `DELETE /api/accounts/{accountId}` - Delete account

### Loan Service (Port 8082)
- `POST /api/loans/create` - Create loan
- `GET /api/loans/all` - Get all loans
- `GET /api/loans/{loanId}` - Get loan by ID
- `GET /api/loans/number/{loanNumber}` - Get by number
- `GET /api/loans/account/{accountId}` - Get by account ID
- `GET /api/loans/active` - Get active loans
- `GET /api/loans/type/{loanType}` - Get by type
- `PUT /api/loans/{loanId}` - Update loan
- `DELETE /api/loans/{loanId}` - Delete loan

## Database Schema

### Accounts Table
```sql
CREATE TABLE accounts (
  account_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account_number VARCHAR(50) NOT NULL UNIQUE,
  customer_name VARCHAR(100) NOT NULL,
  email_address VARCHAR(100),
  phone_number VARCHAR(20),
  balance DECIMAL(19,2) NOT NULL,
  account_type VARCHAR(50),
  account_status VARCHAR(20),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
```

### Loans Table
```sql
CREATE TABLE loans (
  loan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  loan_number VARCHAR(50) NOT NULL UNIQUE,
  account_id BIGINT NOT NULL,
  loan_amount DECIMAL(19,2) NOT NULL,
  interest_rate DECIMAL(5,2) NOT NULL,
  loan_tenure_months INT NOT NULL,
  loan_type VARCHAR(50),
  loan_status VARCHAR(20),
  monthly_emi DECIMAL(19,2),
  start_date DATE,
  end_date DATE,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
```

## Next Steps & Enhancements

### Phase 1: Service Communication
- [ ] Implement RestTemplate for inter-service calls
- [ ] Account service calls Loan service to get customer's loans
- [ ] Add circuit breaker for resilience

### Phase 2: API Gateway
- [ ] Implement Spring Cloud Gateway
- [ ] Unified API endpoint for both services
- [ ] Request routing and load balancing

### Phase 3: Security
- [ ] Implement JWT authentication
- [ ] Add authorization with role-based access
- [ ] Secure inter-service communication

### Phase 4: Service Discovery
- [ ] Implement Eureka for service registration
- [ ] Dynamic service discovery
- [ ] Load balancing

### Phase 5: Monitoring & Logging
- [ ] Add ELK stack (Elasticsearch, Logstash, Kibana)
- [ ] Prometheus metrics collection
- [ ] Distributed tracing with Sleuth and Zipkin

### Phase 6: Containerization
- [ ] Create Dockerfiles for both services
- [ ] Docker Compose for local development
- [ ] Image optimization and security scanning

### Phase 7: Orchestration
- [ ] Deploy on Kubernetes
- [ ] Helm charts for deployment
- [ ] Auto-scaling configuration

### Phase 8: Data Persistence
- [ ] Replace H2 with PostgreSQL/MySQL
- [ ] Add database migrations (Flyway/Liquibase)
- [ ] Implement backup and recovery

### Phase 9: Testing
- [ ] Comprehensive unit tests with 80%+ coverage
- [ ] Integration tests with TestContainers
- [ ] Contract testing for service interactions
- [ ] Load testing with JMeter

### Phase 10: CI/CD
- [ ] GitHub Actions or Jenkins pipeline
- [ ] Automated build, test, and deployment
- [ ] Code quality analysis with SonarQube

## Running Tests

```bash
# Account Service Tests
cd account-service
mvn test

# Loan Service Tests
cd ../loan-service
mvn test

# All Tests with Coverage
mvn clean test jacoco:report
```

## Troubleshooting

### Port Already in Use
```bash
# Kill process on port 8081
lsof -ti :8081 | xargs kill -9

# Kill process on port 8082
lsof -ti :8082 | xargs kill -9
```

### H2 Console Connection Issues
- Ensure service is running
- Check correct URL (http://localhost:8081/h2-console or :8082)
- JDBC URL should match application.properties
- Username: `sa`, Password: empty

### Maven Build Issues
```bash
# Clean all artifacts
mvn clean

# Rebuild with dependencies
mvn clean install -U

# Skip tests during build
mvn clean install -DskipTests
```

## Learning Resources

- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Microservices Patterns: https://microservices.io
- RESTful API Best Practices: https://restfulapi.net

## License

This is an educational project for CTS 7-Week Training.

---

**Created**: January 2024
**Status**: ✅ Complete and Ready for Use
