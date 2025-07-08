package io.carrie.employee;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeApplicationTests {

	@Test
	void contextLoads() {
	}

}

/*
 * NOTE -
 * You can run this test in your IDE or on the command line (by running ./mvnw
 * test or ./gradlew test), and it should pass
 */