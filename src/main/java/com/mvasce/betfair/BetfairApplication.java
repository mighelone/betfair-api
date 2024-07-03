package com.mvasce.betfair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BetfairConfiguration.class)
public class BetfairApplication {
    public static void main(String[] args) {
        SpringApplication.run(BetfairApplication.class, args);
    }
}
