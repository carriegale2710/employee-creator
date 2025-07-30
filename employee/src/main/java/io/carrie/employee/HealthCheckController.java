package io.carrie.employee;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public String health() {
        log.info("Health check endpoint accessed");
        log.debug("Performing health check validation");

        try {
            // Basic health check - you can expand this with database checks, etc.
            String status = "OK";
            log.info("Health check completed successfully - Status: {}", status);
            return status;
        } catch (Exception e) {
            log.error("Health check failed with error: {}", e.getMessage(), e);
            return "ERROR";
        }
    }
}
