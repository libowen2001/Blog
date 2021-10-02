package com.example.blog.service;

import com.example.blog.dao.UserRepository;
import com.example.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkuser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username,password);
        return user;
    }
}
