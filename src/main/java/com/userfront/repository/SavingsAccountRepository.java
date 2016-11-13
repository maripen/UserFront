package com.userfront.repository;

import com.userfront.domain.SavingsAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by maripen on 2016. 11. 13..
 */
public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Long> {
    SavingsAccount findByAccountNumber(int accountNumber);
}
