package io.carrie.employee;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HealthCheckController {
    @GetMapping("/healthcheck")
    public String health() {
        return "OK";
    }
}
