package com.userfront.service;

import com.userfront.domain.User;

import java.util.List;

/**
 * Created by maripen on 2016. 11. 13..
 */
public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    boolean checkIfUserExists(String userName, String email);

    boolean checkIfUsernameExists(String username);

    boolean checkIfEmailExists(String email);

    User createUser(User user);

    User saveUser(User user);

    List<User> findUserList();

    void enableUser(String username);

    void disableUser(String username);
}
