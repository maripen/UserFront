package com.userfront.service.UserServiceImpl;

import com.userfront.repository.UserRepository;
import com.userfront.domain.User;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maripen on 2016. 11. 13..
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        if(checkIfUsernameExists(userName) || checkIfEmailExists(email)) {
            return true;
        }
        return false;
    }

    public boolean checkIfUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }
        return false;
    }

    public boolean checkIfEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }
        return false;
    }

}
