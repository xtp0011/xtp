package com.xtp.customeruser;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class CustomerUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerUserApplication.class, args);
    }

}
