package com.microservice.loan.controller;

import com.microservice.loan.entity.Loan;
import com.microservice.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*")
public class LoanController {
    
    @Autowired
    private LoanService loanService;
    
    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan newLoan = loanService.createLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
    }
    
    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long loanId) {
        Optional<Loan> loan = loanService.getLoanById(loanId);
        return loan.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/number/{loanNumber}")
    public ResponseEntity<Loan> getLoanByNumber(@PathVariable String loanNumber) {
        Optional<Loan> loan = loanService.getLoanByNumber(loanNumber);
        return loan.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }
    
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Loan>> getLoansByAccount(@PathVariable Long accountId) {
        List<Loan> loans = loanService.getLoansByAccountId(accountId);
        return ResponseEntity.ok(loans);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        List<Loan> loans = loanService.getActiveLoan();
        return ResponseEntity.ok(loans);
    }
    
    @GetMapping("/type/{loanType}")
    public ResponseEntity<List<Loan>> getLoansByType(@PathVariable String loanType) {
        List<Loan> loans = loanService.getLoansByType(loanType);
        return ResponseEntity.ok(loans);
    }
    
    @PutMapping("/{loanId}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long loanId, @RequestBody Loan loanDetails) {
        Loan updatedLoan = loanService.updateLoan(loanId, loanDetails);
        
        if (updatedLoan != null) {
            return ResponseEntity.ok(updatedLoan);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        if (loanService.deleteLoan(loanId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
