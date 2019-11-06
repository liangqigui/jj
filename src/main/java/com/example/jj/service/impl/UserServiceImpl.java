package com.example.jj.service.impl;

import com.example.jj.bean.User;
import com.example.jj.mapper.UserMapper;
import com.example.jj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {

        return userMapper.login(user);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
}
