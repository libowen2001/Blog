package com.example.blog.service;

import com.example.blog.po.User;

public interface UserService {
    User checkuser(String username, String password);
}
