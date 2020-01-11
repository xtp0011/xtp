package com.xtp.provideruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //标识EurekaClient
public class ProviderUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderUserApplication.class, args);
    }

}
