package com.userfront.service.ServiceImpl;

import com.userfront.domain.User;
import com.userfront.domain.security.Role;
import com.userfront.domain.security.UserRole;
import com.userfront.repository.RoleRepository;
import com.userfront.repository.UserRepository;
import com.userfront.service.AccountService;
import com.userfront.service.RoleService;
import com.userfront.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maripen on 2016. 11. 13..
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private AccountService accountService;
    private RoleService roleService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, roleService.findByName(Role.USER)));

        return createUser(user, userRoles);
    }

    private User createUser(User user, Set<UserRole> userRoles) {
        User localUser = findByUsername(user.getUsername());

        if (null != localUser) {
            LOG.info("User with username {} already exists. Nothing will be done");
        } else {
            String encryptedPassord = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassord);

            userRoles.forEach(userRole -> roleRepository.save(userRole.getRole()));

            user.getUserRoles().addAll(userRoles);

            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());

            localUser = userRepository.save(user);
        }
        return localUser;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkIfUserExists(String userName, String email) {
        return checkIfUsernameExists(userName) || checkIfEmailExists(email);
    }

    public boolean checkIfUsernameExists(String username) {
        return null != findByUsername(username);
    }

    public boolean checkIfEmailExists(String email) {
        return null != findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
