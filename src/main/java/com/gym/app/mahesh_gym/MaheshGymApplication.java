package com.gym.app.mahesh_gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MaheshGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaheshGymApplication.class, args);
    }

}
