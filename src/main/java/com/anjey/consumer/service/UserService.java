package com.anjey.consumer.service;

import com.anjey.consumer.entity.User;
import com.anjey.consumer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Transactional
    public void createUser(String username, String password, Set roles) {
        userRepository.createUser(username, password, roles);
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

}
