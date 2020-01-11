package com.xtp.customeruser.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod = "fallBackHello")
    public String hello(@PathVariable String name){
        return  helloFegin.hello(name);
    }

    //断路器方法,返回值与方法返回值一致，参数也要一致
    //主要bug,当服务刚启动时，第一次访问就到断路器，不正常，
    //第一次进入不算数
    public String fallBackHello(String name){
        return "南少霞";
        //当发生异常时，直接返回默认值，这种形式称为：降级
    }


}
