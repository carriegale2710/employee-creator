package io.carrie.employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SmokeTest {

    @Autowired
    private HomeController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void greeting_calledByHomeController() {
        assertThat(controller.greeting()).contains("Hello, World");
    }

}

/*
 * NOTE - Sanity test to check app runs at all
 * You can run this test in your IDE or on the command line
 * (by running ./mvnw test), and it should pass
 */