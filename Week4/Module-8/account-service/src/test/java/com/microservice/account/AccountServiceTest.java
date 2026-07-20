package com.microservice.account;

import com.microservice.account.entity.Account;
import com.microservice.account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTest {
    
    @Autowired
    private AccountService accountService;
    
    private Account testAccount;
    
    @BeforeEach
    public void setUp() {
        testAccount = new Account();
        testAccount.setAccountNumber("TEST001");
        testAccount.setCustomerName("Test Customer");
        testAccount.setEmailAddress("test@example.com");
        testAccount.setPhoneNumber("9876543210");
        testAccount.setBalance(new BigDecimal("10000"));
        testAccount.setAccountType("Savings");
    }
    
    @Test
    public void testCreateAccount() {
        Account createdAccount = accountService.createAccount(testAccount);
        
        assertNotNull(createdAccount);
        assertNotNull(createdAccount.getAccountId());
        assertEquals("TEST001", createdAccount.getAccountNumber());
        assertEquals("Test Customer", createdAccount.getCustomerName());
        assertEquals("ACTIVE", createdAccount.getAccountStatus());
    }
    
    @Test
    public void testGetAccountById() {
        Account createdAccount = accountService.createAccount(testAccount);
        Account retrievedAccount = accountService.getAccountById(createdAccount.getAccountId()).orElse(null);
        
        assertNotNull(retrievedAccount);
        assertEquals(createdAccount.getAccountId(), retrievedAccount.getAccountId());
        assertEquals(createdAccount.getAccountNumber(), retrievedAccount.getAccountNumber());
    }
    
    @Test
    public void testGetAccountByNumber() {
        accountService.createAccount(testAccount);
        Account retrievedAccount = accountService.getAccountByNumber("TEST001").orElse(null);
        
        assertNotNull(retrievedAccount);
        assertEquals("TEST001", retrievedAccount.getAccountNumber());
    }
    
    @Test
    public void testGetAllAccounts() {
        accountService.createAccount(testAccount);
        
        var allAccounts = accountService.getAllAccounts();
        assertNotNull(allAccounts);
        assertTrue(allAccounts.size() > 0);
    }
    
    @Test
    public void testUpdateAccount() {
        Account createdAccount = accountService.createAccount(testAccount);
        createdAccount.setCustomerName("Updated Customer");
        createdAccount.setBalance(new BigDecimal("20000"));
        
        Account updatedAccount = accountService.updateAccount(createdAccount.getAccountId(), createdAccount);
        
        assertNotNull(updatedAccount);
        assertEquals("Updated Customer", updatedAccount.getCustomerName());
        assertEquals(new BigDecimal("20000"), updatedAccount.getBalance());
    }
    
    @Test
    public void testDeleteAccount() {
        Account createdAccount = accountService.createAccount(testAccount);
        boolean isDeleted = accountService.deleteAccount(createdAccount.getAccountId());
        
        assertTrue(isDeleted);
        assertTrue(accountService.getAccountById(createdAccount.getAccountId()).isEmpty());
    }
}
