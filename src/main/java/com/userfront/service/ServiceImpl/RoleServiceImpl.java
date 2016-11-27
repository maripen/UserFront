package com.userfront.service.ServiceImpl;

import com.userfront.domain.security.Role;
import com.userfront.repository.RoleRepository;
import com.userfront.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maripen on 2016. 11. 13..
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
       return roleRepository.findByName(name);
    }
}
