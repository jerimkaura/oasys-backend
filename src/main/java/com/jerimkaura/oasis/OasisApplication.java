package com.jerimkaura.oasis;

import com.jerimkaura.oasis.config.OasisProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties(OasisProperties.class)
@PropertySource("classpath:/application-dev.properties")
public class OasisApplication {

    public static void main(String[] args) {
        SpringApplication.run(OasisApplication.class, args);
    }
}
