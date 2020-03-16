package ru.pipan.boot2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ConsulHealthCheckController {

    @GetMapping(value = "/consul/health/check", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String healthCheckEndpoint() {
        logger.warn("Consul is checking our health :)");
        return "{}";
    }
}
