package com.blackburn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.blackburn")
@EnableJpaRepositories(basePackages = "com.blackburn")
@PropertySource(value = "classpath:application.properties")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
