package com.userfront.service;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;

import java.security.Principal;

/**
 * Created by maripen on 2016. 11. 13..
 */
public interface AccountService {
    PrimaryAccount createPrimaryAccount();

    SavingsAccount createSavingsAccount();

    void deposit(String accountType, double amount, Principal principal);

    void withdraw(String accountType, double amount, Principal principal);
}
