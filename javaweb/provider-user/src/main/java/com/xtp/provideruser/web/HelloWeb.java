package com.xtp.provideruser.web;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWeb {
    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable String name){
        return "one spingboot hello " +name;
    }
}
