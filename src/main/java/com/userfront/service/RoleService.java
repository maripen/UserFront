package com.userfront.service;

import com.userfront.domain.security.Role;

/**
 * Created by maripen on 2016. 11. 13..
 */
public interface RoleService {
    Role findByName(String name);
}
