package com.shivam.LoginApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shivam.LoginApp.security.JwtConfig;

/**
 * Main application class for the Login Application.
 * Entry point for the Spring Boot application.
 */
@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
/**
 * The main class for the Spring Boot application. It initializes the
 * application context and starts the embedded server.
 */
@EnableJpaRepositories(basePackages = "com.shivam.LoginApp.repository")
public class LoginAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginAppApplication.class, args);
    }
}