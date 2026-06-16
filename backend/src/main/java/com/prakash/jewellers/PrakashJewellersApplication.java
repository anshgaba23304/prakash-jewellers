package com.prakash.jewellers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrakashJewellersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrakashJewellersApplication.class, args);
    }
}
