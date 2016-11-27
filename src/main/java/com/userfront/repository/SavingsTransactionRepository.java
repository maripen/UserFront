package com.userfront.repository;

import com.userfront.domain.SavingsTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maripen on 2016. 11. 27..
 */
@Repository
public interface SavingsTransactionRepository extends CrudRepository<SavingsTransaction, Long> {
    List<SavingsTransaction> findAll();
}
