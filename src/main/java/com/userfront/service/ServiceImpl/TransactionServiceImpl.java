package com.userfront.service.ServiceImpl;

import com.userfront.domain.*;
import com.userfront.repository.PrimaryAccountRepository;
import com.userfront.repository.PrimaryTransactionRepository;
import com.userfront.repository.SavingsAccountRepository;
import com.userfront.repository.SavingsTransactionRepository;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by maripen on 2016. 11. 27..
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private UserService userService;
    private PrimaryTransactionRepository primaryTransactionRepository;
    private SavingsTransactionRepository savingsTransactionRepository;
    private PrimaryAccountRepository primaryAccountRepository;
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    public TransactionServiceImpl(UserService userService,
                                  PrimaryTransactionRepository primaryTransactionRepository,
                                  SavingsTransactionRepository savingsTransactionRepository,
                                  PrimaryAccountRepository primaryAccountRepository,
                                  SavingsAccountRepository savingsAccountRepository) {
        this.userService = userService;
        this.primaryTransactionRepository = primaryTransactionRepository;
        this.savingsTransactionRepository = savingsTransactionRepository;
        this.primaryAccountRepository = primaryAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
    }

    @Override
    public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
        User user = userService.findByUsername(username);

        return user.getPrimaryAccount().getTransactionList();
    }

    @Override
    public List<SavingsTransaction> findSavingsTransactionList(String username) {
        User user = userService.findByUsername(username);

        return user.getSavingsAccount().getSavingsTransactionList();
    }

    @Override
    public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionRepository.save(primaryTransaction);
    }

    @Override
    public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionRepository.save(savingsTransaction);
    }

    @Override
    public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionRepository.save(primaryTransaction);
    }

    @Override
    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionRepository.save(savingsTransaction);
    }

    @Override
    public void betweenAccountsTransfer(String transferFrom,
                                        String transferTo,
                                        String amount,
                                        PrimaryAccount primaryAccount,
                                        SavingsAccount savingsAccount) throws Exception {
        Date date = new Date();

        if ("Primary".equals(transferFrom) && "Savings".equalsIgnoreCase(transferTo)) {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);
            savingsAccountRepository.save(savingsAccount);

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
                    "Between account transfer from " + transferFrom + " to " + transferTo,
                    "Transfer",
                    "Finished",
                    new Double(amount),
                    primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)),
                    primaryAccount);
            primaryTransactionRepository.save(primaryTransaction);
        } else if ("Savings".equals(transferFrom) && "Primary".equalsIgnoreCase(transferTo)) {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountRepository.save(primaryAccount);
            savingsAccountRepository.save(savingsAccount);

            SavingsTransaction savingsTransaction = new SavingsTransaction(date,
                    "Between account transfer from " + transferFrom + " to " + transferTo,
                    "Transfer",
                    "Finished",
                    new Double(amount),
                    savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)),
                    savingsAccount);
            savingsTransactionRepository.save(savingsTransaction);
        } else {
            throw new Exception("Invalid transfer");
        }
    }
}
