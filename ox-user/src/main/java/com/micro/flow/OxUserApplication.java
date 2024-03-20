package com.micro.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OxUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(OxUserApplication.class, args);
    }

}
