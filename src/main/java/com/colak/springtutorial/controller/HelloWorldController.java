package com.colak.springtutorial.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/hello")

@Slf4j
public class HelloWorldController {


    // The @Bulkhead annotation allows you to control the number of concurrent calls to a particular method or service.
    // If the limit is exceeded, the remaining calls will either be rejected immediately or queued (depending on the configuration).
    @GetMapping
    @Bulkhead(name = "bulkhead", fallbackMethod = "fallback")
    public ResponseEntity<String> showHelloWorld() {
        return new ResponseEntity<>("bulkhead",HttpStatus.OK);
    }

    public ResponseEntity<String> fallback(Throwable e) {
        log.error("fallback exception , {}", e.getMessage());
        return new ResponseEntity<>("your request failed with bulkhead", HttpStatus.OK);
    }
}
