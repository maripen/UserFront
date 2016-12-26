package com.userfront.repository;

import com.userfront.domain.PrimaryAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by maripen on 2016. 11. 13..
 */
@Repository
public interface PrimaryAccountRepository extends CrudRepository<PrimaryAccount, Long> {
    PrimaryAccount findByAccountNumber(int accountNumber);
}
