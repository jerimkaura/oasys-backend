package com.jerimkaura.oasis;

import com.jerimkaura.oasis.config.OasisProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OasisProperties.class)
public class OasisApplication {

    public static void main(String[] args) {
        SpringApplication.run(OasisApplication.class, args);
    }
}
