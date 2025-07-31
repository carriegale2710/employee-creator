package io.carrie.employee;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public ResponseEntity<String> health() {

        // This is a simple health check endpoint that can be
        // used to verify if the application is running.
        log.info("Health check endpoint accessed");

        try {
            log.debug("Performing health check validation");
            String status = "OK";
            log.info("Health check completed successfully - Status: {}", status);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            log.error("Health check failed with error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
        }
    }
}
