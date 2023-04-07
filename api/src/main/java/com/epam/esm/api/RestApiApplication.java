package com.epam.esm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.epam.esm")
@EntityScan(basePackages = "com.epam.esm.core.model.domain")
@EnableJpaRepositories(basePackages = "com.epam.esm.dao")
public class RestApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestApiApplication.class, args);
    }
}
