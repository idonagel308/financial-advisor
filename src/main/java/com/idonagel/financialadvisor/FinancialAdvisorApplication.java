package com.idonagel.financialadvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Starting point of the app.
 * <p>
 * Running {@code main} starts Spring Boot: it starts the built-in web server (Tomcat) on port 8080,
 * finds this app's classes in this package and the packages below it, and keeps the server
 * running until it is stopped.
 */
@SpringBootApplication
public class FinancialAdvisorApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinancialAdvisorApplication.class, args);
    }
}
