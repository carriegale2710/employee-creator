package io.carrie.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        log.info("Home endpoint accessed - serving greeting message");
        log.debug("Processing request for root URL '/'");

        try {
            String message = "Hello, World";
            log.info("Successfully served greeting message: '{}'", message);
            return message;
        } catch (Exception e) {
            log.error("Error serving home page: {}", e.getMessage(), e);
            return "Error loading page";
        }
    }
}

/*
 * The preceding example does not specify GET versus PUT, POST, and so forth.
 * By default @RequestMapping maps all HTTP operations.
 * Use @GetMapping or @RequestMapping(method=GET) to narrow this mapping.
 */