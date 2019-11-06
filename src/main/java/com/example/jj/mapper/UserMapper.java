package com.example.jj.mapper;

import com.example.jj.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User login(User user);
    int addUser(User user);
}
