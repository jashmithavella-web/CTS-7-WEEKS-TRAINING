package com.microservice.account.controller;

import com.microservice.account.entity.Account;
import com.microservice.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account newAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
        Optional<Account> account = accountService.getAccountById(accountId);
        return account.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
        Optional<Account> account = accountService.getAccountByNumber(accountNumber);
        return account.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<Account>> getAccountsByCustomer(@PathVariable String customerName) {
        List<Account> accounts = accountService.getAccountsByCustomerName(customerName);
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Account>> getActiveAccounts() {
        List<Account> accounts = accountService.getActiveAccounts();
        return ResponseEntity.ok(accounts);
    }
    
    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account accountDetails) {
        Account updatedAccount = accountService.updateAccount(accountId, accountDetails);
        
        if (updatedAccount != null) {
            return ResponseEntity.ok(updatedAccount);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        if (accountService.deleteAccount(accountId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
