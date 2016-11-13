package com.userfront.repository;

import com.userfront.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by maripen on 2016. 11. 13..
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
