package io.carrie.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }
}

/*
 * The preceding example does not specify GET versus PUT, POST, and so forth.
 * By default @RequestMapping maps all HTTP operations.
 * Use @GetMapping or @RequestMapping(method=GET) to narrow this mapping.
 */