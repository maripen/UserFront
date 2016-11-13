package com.userfront.service;

import com.userfront.domain.User;

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

}
