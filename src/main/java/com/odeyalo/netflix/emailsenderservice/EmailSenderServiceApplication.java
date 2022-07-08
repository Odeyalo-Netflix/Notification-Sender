package com.odeyalo.netflix.emailsenderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableEurekaClient
@EnableKafka
public class EmailSenderServiceApplication {

    public static void main(String[] args) {
       SpringApplication.run(EmailSenderServiceApplication.class, args);
    }
}
