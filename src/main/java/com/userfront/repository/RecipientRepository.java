package com.userfront.repository;

import com.userfront.domain.Recipient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by maripen on 2016. 12. 26..
 */
public interface RecipientRepository extends CrudRepository<Recipient, Long> {
    List<Recipient> findAll();

    Recipient findByName(String recipientName);

    void deleteByName(String recipientName);
}
