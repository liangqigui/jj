package com.example.jj.service;

import com.example.jj.bean.User;

public interface IUserService {
    User login(User user);
    int addUser(User user);
}
