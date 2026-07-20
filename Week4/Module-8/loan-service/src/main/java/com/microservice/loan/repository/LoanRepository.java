package com.microservice.loan.repository;

import com.microservice.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByLoanNumber(String loanNumber);
    List<Loan> findByAccountId(Long accountId);
    List<Loan> findByLoanStatus(String status);
    List<Loan> findByLoanType(String loanType);
}
