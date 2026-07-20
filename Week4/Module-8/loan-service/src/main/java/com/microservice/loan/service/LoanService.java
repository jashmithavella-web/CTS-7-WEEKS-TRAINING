package com.microservice.loan.service;

import com.microservice.loan.entity.Loan;
import com.microservice.loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;
    
    public Loan createLoan(Loan loan) {
        // Calculate monthly EMI
        calculateMonthlyEMI(loan);
        return loanRepository.save(loan);
    }
    
    public Optional<Loan> getLoanById(Long loanId) {
        return loanRepository.findById(loanId);
    }
    
    public Optional<Loan> getLoanByNumber(String loanNumber) {
        return loanRepository.findByLoanNumber(loanNumber);
    }
    
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    
    public List<Loan> getLoansByAccountId(Long accountId) {
        return loanRepository.findByAccountId(accountId);
    }
    
    public List<Loan> getActiveLoan() {
        return loanRepository.findByLoanStatus("ACTIVE");
    }
    
    public List<Loan> getLoansByType(String loanType) {
        return loanRepository.findByLoanType(loanType);
    }
    
    public Loan updateLoan(Long loanId, Loan loanDetails) {
        Optional<Loan> existingLoan = loanRepository.findById(loanId);
        
        if (existingLoan.isPresent()) {
            Loan loan = existingLoan.get();
            loan.setLoanAmount(loanDetails.getLoanAmount());
            loan.setInterestRate(loanDetails.getInterestRate());
            loan.setLoanTenureMonths(loanDetails.getLoanTenureMonths());
            loan.setLoanStatus(loanDetails.getLoanStatus());
            
            // Recalculate EMI
            calculateMonthlyEMI(loan);
            
            return loanRepository.save(loan);
        }
        
        return null;
    }
    
    public boolean deleteLoan(Long loanId) {
        if (loanRepository.existsById(loanId)) {
            loanRepository.deleteById(loanId);
            return true;
        }
        return false;
    }
    
    // Helper method to calculate monthly EMI
    private void calculateMonthlyEMI(Loan loan) {
        // EMI = P * r * (1 + r)^n / ((1 + r)^n - 1)
        // where P = Principal, r = Monthly Interest Rate, n = Number of Months
        
        BigDecimal principal = loan.getLoanAmount();
        BigDecimal annualRate = loan.getInterestRate();
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 6, BigDecimal.ROUND_HALF_UP);
        BigDecimal monthlyRateDecimal = monthlyRate.divide(BigDecimal.valueOf(100), 6, BigDecimal.ROUND_HALF_UP);
        
        int months = loan.getLoanTenureMonths();
        
        BigDecimal numerator = monthlyRateDecimal.multiply(
            BigDecimal.valueOf(Math.pow(1 + monthlyRateDecimal.doubleValue(), months))
        );
        BigDecimal denominator = BigDecimal.valueOf(
            Math.pow(1 + monthlyRateDecimal.doubleValue(), months) - 1
        );
        
        BigDecimal emi = principal.multiply(numerator).divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
        loan.setMonthlyEMI(emi);
    }
}
