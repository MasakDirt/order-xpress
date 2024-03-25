package com.micro.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OxClothesApplication {

    public static void main(String[] args) {
        SpringApplication.run(OxClothesApplication.class, args);
    }

}
