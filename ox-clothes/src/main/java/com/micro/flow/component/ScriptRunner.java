package com.micro.flow.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ScriptRunner implements ApplicationRunner {

    @Autowired
    @Qualifier("webApplicationContext")
    private ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        Resource resource = resourceLoader
                .getResource("classpath:scripts/scripts-initializer.sh");

        String command = "powershell.exe " + resource.getFile().getAbsolutePath();
        Runtime.getRuntime().exec(command);
        log.info("MongoDB data initialization successfully done!");
    }

}
