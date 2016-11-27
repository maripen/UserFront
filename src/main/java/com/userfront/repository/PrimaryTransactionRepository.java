package com.userfront.repository;

import com.userfront.domain.PrimaryTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maripen on 2016. 11. 27..
 */
@Repository
public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, Long> {
    List<PrimaryTransaction> findAll();
}
