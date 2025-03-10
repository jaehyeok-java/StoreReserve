package com.project.storereserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StoreReserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreReserveApplication.class, args);
    }

}
