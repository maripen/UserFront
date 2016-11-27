package com.userfront.service;

import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsTransaction;

import java.util.List;

/**
 * Created by maripen on 2016. 11. 27..
 */
public interface TransactionService {

    List<PrimaryTransaction> findPrimaryTransactionList(String username);

    List<SavingsTransaction> findSavingsTransactionList(String username);

    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);

    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
}
