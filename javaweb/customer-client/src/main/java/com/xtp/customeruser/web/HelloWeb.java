package com.xtp.customeruser.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController//调用服务的提供者
public class HelloWeb {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable String name){
       // String url = "http://localhost:7900/hello/"+name;//提供url
        String url = "http://provider-user/hello/"+name;
        return  restTemplate.getForObject(url,String.class);
    }

}
