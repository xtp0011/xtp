package com.xtp.customeruser;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@SpringCloudApplication
@EnableZuulServer
public class CustomerUserApplication {
    public static void main(String[] args) {

        SpringApplication.run(CustomerUserApplication.class, args);
    }

}
