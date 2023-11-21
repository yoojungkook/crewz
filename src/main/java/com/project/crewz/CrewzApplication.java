package com.project.crewz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CrewzApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrewzApplication.class, args);
    }

}
