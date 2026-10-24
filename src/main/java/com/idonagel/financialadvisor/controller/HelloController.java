package com.idonagel.financialadvisor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * A test endpoint that shows the web server is working.
 * <p>
 * Answers GET requests to {@code /api/hello} with a fixed greeting. It has no logic of its own:
 * it only proves that a request reaches the app and that a response comes back.
 */
@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello world";
    }
}
