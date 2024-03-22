package com.micro.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class OxConfigServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OxConfigServiceApplication.class, args);
    }

}
