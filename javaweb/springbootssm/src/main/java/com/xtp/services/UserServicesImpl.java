package com.xtp.services;

import com.xtp.mapper.UserMapper;
import com.xtp.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices{

    @Autowired
    private UserMapper userMapper;

    public List<User> findAll(){
        return userMapper.findAll();
    }
}
