package com.avr.avrbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AvrBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvrBackendApplication.class, args);
    }
}
