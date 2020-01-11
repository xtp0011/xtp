package com.xtp.web;

import com.xtp.pojo.User;
import com.xtp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userServices.findAll();
    }
}
