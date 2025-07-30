package io.carrie.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class EmployeeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("This is a console message");

		// Uncomment to test logging levels
		// log.info("INFO:Test");
		// log.warn("WARN:Test");
		// log.error("ERROR:Test");
		// log.trace("TRACE:Test");
		// log.debug("DEBUG:Test");
	}
}
