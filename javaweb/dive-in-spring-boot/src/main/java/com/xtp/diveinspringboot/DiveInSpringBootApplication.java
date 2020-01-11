package com.xtp.diveinspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@ServletComponentScan(basePackages = "com.xtp.diveinspringboot.web.servlet")
public class DiveInSpringBootApplication {

    public static void main(String[] args) {
       /* new SpringApplicationBuilder(DiveInSpringBootApplication.class)
                //.web(WebApplicationType.NONE)
                .run(args);*/
        //等价
        SpringApplication.run(DiveInSpringBootApplication.class, args);
    }

}
