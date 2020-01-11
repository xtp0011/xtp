package com.xtp.customeruser.web;

import com.xtp.customeruser.feign.HelloFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//调用服务的提供者
public class HelloWeb {

    @Autowired
    private HelloFegin helloFegin;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable String name){
        return  helloFegin.hello(name);
    }

}
