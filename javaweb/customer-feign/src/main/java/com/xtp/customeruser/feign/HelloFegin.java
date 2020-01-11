package com.xtp.customeruser.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 调用提供者
 */
@FeignClient("provider-user")
public interface HelloFegin {
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable("name") String name);
}
