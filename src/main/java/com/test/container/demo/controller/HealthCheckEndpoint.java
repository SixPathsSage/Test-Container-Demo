package com.test.container.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckEndpoint {

    @GetMapping("/hearbeat")
    public ResponseEntity<Void> heartbeat() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
