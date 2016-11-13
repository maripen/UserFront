package com.userfront.repository;

import com.userfront.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by maripen on 2016. 11. 13..
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
