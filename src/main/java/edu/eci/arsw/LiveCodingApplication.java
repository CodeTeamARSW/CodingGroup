package edu.eci.arsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan(basePackages = {"edu.eci.arsw.model"})
//@ComponentScan(basePackages = {"edu.eci.arsw"})
//@EnableJpaAuditing
//@EnableJpaRepositories(basePackages="edu.eci.arsw.repository", entityManagerFactoryRef="emf")
public class LiveCodingApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiveCodingApplication.class, args);
    }
}