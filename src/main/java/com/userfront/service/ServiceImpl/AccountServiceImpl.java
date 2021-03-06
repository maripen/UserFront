package com.userfront.service.ServiceImpl;

import com.userfront.domain.*;
import com.userfront.repository.PrimaryAccountRepository;
import com.userfront.repository.SavingsAccountRepository;
import com.userfront.service.AccountService;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

/**
 * Created by maripen on 2016. 11. 13..
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static int nextAccountNumber = 1234567;

    private PrimaryAccountRepository primaryAccountRepository;
    private SavingsAccountRepository savingsAccountRepository;
    private TransactionService transactionService;

    private UserService userService;

    @Autowired
    public AccountServiceImpl(PrimaryAccountRepository primaryAccountRepository,
                              SavingsAccountRepository savingsAccountRepository,
                              TransactionService transactionService,
                              UserService userService) {
        this.primaryAccountRepository = primaryAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountGen());

        primaryAccountRepository.save(primaryAccount);

        return primaryAccountRepository.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    @Override
    public SavingsAccount createSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountGen());

        savingsAccountRepository.save(savingsAccount);

        return savingsAccountRepository.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    private int accountGen() {
        return ++nextAccountNumber;
    }

    @Override
    public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if ("primary".equalsIgnoreCase(accountType)) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);

            Date date = new Date();
            PrimaryTransaction primaryDepositTransaction = new PrimaryTransaction(date,
                    "Deposit to Primary Account",
                    "Account",
                    "Finished",
                    amount,
                    primaryAccount.getAccountBalance(),
                    primaryAccount);

            transactionService.savePrimaryDepositTransaction(primaryDepositTransaction);

        } else if ("savings".equalsIgnoreCase(accountType)) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsDepositTransaction = new SavingsTransaction(date,
                    "Deposit to Savings Account",
                    "Account",
                    "Finished",
                    amount,
                    savingsAccount.getAccountBalance(),
                    savingsAccount);

            transactionService.saveSavingsDepositTransaction(savingsDepositTransaction);
        }
    }

    @Override
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if ("primary".equalsIgnoreCase(accountType)) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);

            Date date = new Date();
            PrimaryTransaction primaryWithdrawTransaction = new PrimaryTransaction(date,
                    "Withdraw to Primary Account",
                    "Account",
                    "Finished",
                    amount,
                    primaryAccount.getAccountBalance(),
                    primaryAccount);

            transactionService.savePrimaryWithdrawTransaction(primaryWithdrawTransaction);

        } else if ("savings".equalsIgnoreCase(accountType)) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsWithdrawTransaction = new SavingsTransaction(date,
                    "Withdraw to Savings Account",
                    "Account",
                    "Finished",
                    amount,
                    savingsAccount.getAccountBalance(),
                    savingsAccount);

            transactionService.saveSavingsWithdrawTransaction(savingsWithdrawTransaction);
        }
    }
}
