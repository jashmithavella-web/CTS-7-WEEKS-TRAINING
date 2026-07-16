package com.microservice.loan;

import com.microservice.loan.entity.Loan;
import com.microservice.loan.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoanServiceTest {
    
    @Autowired
    private LoanService loanService;
    
    private Loan testLoan;
    
    @BeforeEach
    public void setUp() {
        testLoan = new Loan();
        testLoan.setLoanNumber("LOAN001");
        testLoan.setAccountId(1L);
        testLoan.setLoanAmount(new BigDecimal("500000"));
        testLoan.setInterestRate(new BigDecimal("8.5"));
        testLoan.setLoanTenureMonths(60);
        testLoan.setLoanType("Home");
    }
    
    @Test
    public void testCreateLoan() {
        Loan createdLoan = loanService.createLoan(testLoan);
        
        assertNotNull(createdLoan);
        assertNotNull(createdLoan.getLoanId());
        assertEquals("LOAN001", createdLoan.getLoanNumber());
        assertEquals(1L, createdLoan.getAccountId());
        assertEquals("ACTIVE", createdLoan.getLoanStatus());
        assertNotNull(createdLoan.getMonthlyEMI());
        assertTrue(createdLoan.getMonthlyEMI().compareTo(BigDecimal.ZERO) > 0);
    }
    
    @Test
    public void testGetLoanById() {
        Loan createdLoan = loanService.createLoan(testLoan);
        Loan retrievedLoan = loanService.getLoanById(createdLoan.getLoanId()).orElse(null);
        
        assertNotNull(retrievedLoan);
        assertEquals(createdLoan.getLoanId(), retrievedLoan.getLoanId());
        assertEquals(createdLoan.getLoanNumber(), retrievedLoan.getLoanNumber());
    }
    
    @Test
    public void testGetLoanByNumber() {
        loanService.createLoan(testLoan);
        Loan retrievedLoan = loanService.getLoanByNumber("LOAN001").orElse(null);
        
        assertNotNull(retrievedLoan);
        assertEquals("LOAN001", retrievedLoan.getLoanNumber());
    }
    
    @Test
    public void testGetLoansByAccountId() {
        loanService.createLoan(testLoan);
        
        var loansByAccount = loanService.getLoansByAccountId(1L);
        assertNotNull(loansByAccount);
        assertTrue(loansByAccount.size() > 0);
    }
    
    @Test
    public void testGetAllLoans() {
        loanService.createLoan(testLoan);
        
        var allLoans = loanService.getAllLoans();
        assertNotNull(allLoans);
        assertTrue(allLoans.size() > 0);
    }
    
    @Test
    public void testUpdateLoan() {
        Loan createdLoan = loanService.createLoan(testLoan);
        createdLoan.setInterestRate(new BigDecimal("9.0"));
        createdLoan.setLoanStatus("CLOSED");
        
        Loan updatedLoan = loanService.updateLoan(createdLoan.getLoanId(), createdLoan);
        
        assertNotNull(updatedLoan);
        assertEquals(new BigDecimal("9.0"), updatedLoan.getInterestRate());
        assertEquals("CLOSED", updatedLoan.getLoanStatus());
    }
    
    @Test
    public void testDeleteLoan() {
        Loan createdLoan = loanService.createLoan(testLoan);
        boolean isDeleted = loanService.deleteLoan(createdLoan.getLoanId());
        
        assertTrue(isDeleted);
        assertTrue(loanService.getLoanById(createdLoan.getLoanId()).isEmpty());
    }
}
