package edu.eci.arsw.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw"})
@EnableJpaRepositories(basePackages = {"edu.eci.arsw.repository"})
@EnableAutoConfiguration
public class LiveCodingApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiveCodingApplication.class, args);
    }
}