package edu.eci.arsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EntityScan(basePackages = {"edu.eci.arsw.model"})
@ComponentScan(basePackages = {"edu.eci.arsw"})
//@EnableJpaAuditing
//@EnableJpaRepositories(basePackages="edu.eci.arsw.repository", entityManagerFactoryRef="emf")
public class LiveCodingApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiveCodingApplication.class, args);
    }
}