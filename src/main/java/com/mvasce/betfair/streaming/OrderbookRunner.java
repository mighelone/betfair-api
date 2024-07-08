package com.mvasce.betfair.streaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class OrderbookRunner {

    public static void main(String[] args) {
        SpringApplication.run(OrderbookRunner.class, args);
    }
}
