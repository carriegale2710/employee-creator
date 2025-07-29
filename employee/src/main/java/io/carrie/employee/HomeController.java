package io.carrie.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        logger.info("Home endpoint accessed - serving greeting message");
        logger.debug("Processing request for root URL '/'");

        try {
            String message = "Hello, World";
            logger.info("Successfully served greeting message: '{}'", message);
            return message;
        } catch (Exception e) {
            logger.error("Error serving home page: {}", e.getMessage(), e);
            return "Error loading page";
        }
    }
}

/*
 * The preceding example does not specify GET versus PUT, POST, and so forth.
 * By default @RequestMapping maps all HTTP operations.
 * Use @GetMapping or @RequestMapping(method=GET) to narrow this mapping.
 */