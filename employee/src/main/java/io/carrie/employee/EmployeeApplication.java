package io.carrie.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(EmployeeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("This is a console message");
		logger.info("INFO:This is an info message");
		logger.warn("WARN:This is a warning message");
		logger.error("ERROR:This is an error message");
		logger.trace("trace message");
		logger.debug("debug message");
	}
}
