package io.carrie.employee;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HealthCheckController {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping("/healthcheck")
    public String health() {
        logger.info("Health check endpoint accessed");
        logger.debug("Performing health check validation");

        try {
            // Basic health check - you can expand this with database checks, etc.
            String status = "OK";
            logger.info("Health check completed successfully - Status: {}", status);
            return status;
        } catch (Exception e) {
            logger.error("Health check failed with error: {}", e.getMessage(), e);
            return "ERROR";
        }
    }
}
