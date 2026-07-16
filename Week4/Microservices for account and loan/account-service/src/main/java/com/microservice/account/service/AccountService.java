package com.microservice.account.service;

import com.microservice.account.entity.Account;
import com.microservice.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
    
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }
    
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    
    public List<Account> getAccountsByCustomerName(String customerName) {
        return accountRepository.findByCustomerName(customerName);
    }
    
    public List<Account> getActiveAccounts() {
        return accountRepository.findByAccountStatus("ACTIVE");
    }
    
    public Account updateAccount(Long accountId, Account accountDetails) {
        Optional<Account> existingAccount = accountRepository.findById(accountId);
        
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            account.setCustomerName(accountDetails.getCustomerName());
            account.setEmailAddress(accountDetails.getEmailAddress());
            account.setPhoneNumber(accountDetails.getPhoneNumber());
            account.setBalance(accountDetails.getBalance());
            account.setAccountStatus(accountDetails.getAccountStatus());
            
            return accountRepository.save(account);
        }
        
        return null;
    }
    
    public boolean deleteAccount(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }
}
