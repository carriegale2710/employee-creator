package io.carrie.employee.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.carrie.employee.contract.Contract;
import io.carrie.employee.contract.ContractRepository;
import io.carrie.employee.contract.enums.ContractType;
import io.carrie.employee.contract.enums.Department;

import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
@Profile({ "dev", "default" }) // Run in dev OR default profile
public class DataSeeder implements CommandLineRunner {
    // executes every time app is run

    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final Faker faker = new Faker(java.util.Locale.forLanguageTag("en-AU"));
    private final Random random = new Random();

    DataSeeder(EmployeeRepository employeeRepository, ContractRepository contractRepository) {
        this.employeeRepository = employeeRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("!!! DATA SEEDER RUNNING !!!");

        // NOTE - Decide amount of data you want
        int numOfEmployees = 20;
        int numOfContracts = 50; // employees can have multiple contracts

        // SECTION: Create employees first
        List<Employee> draftEmployees = new ArrayList<>();

        if (employeeRepository.count() == 0) {
            for (int i = 0; i < numOfEmployees; i++) {
                Employee employee = new Employee();
                employee.setFirstName(faker.name().firstName());
                employee.setLastName(faker.name().lastName());
                employee.setEmail(employee.getFirstName().toLowerCase() + "." + employee.getLastName().toLowerCase()
                        + "@company.com");
                employee.setPhone(faker.phoneNumber().phoneNumber());
                employee.setAddress(faker.address().fullAddress());

                draftEmployees.add(employee);
            }
        }

        // SECTION: Create contracts and link to employees

        List<Contract> draftContracts = new ArrayList<>();

        if (contractRepository.count() == 0) {

            for (int i = 0; i < numOfContracts; i++) { // More contracts than employees for realistic data
                Contract contract = new Contract();
                contract.setContractType(ContractType.values()[random.nextInt(ContractType.values().length)]);
                contract.setDepartment(Department.values()[random.nextInt(Department.values().length)]);
                contract.setSalaryAmount(BigDecimal.valueOf(faker.number().numberBetween(30000, 100000)));
                contract.setHoursPerWeek(faker.number().numberBetween(20, 40));
                contract.setStartDate(LocalDate.now()
                        .minusYears(faker.number().numberBetween(0, 5))
                        .minusMonths(faker.number().numberBetween(0, 11))
                        .minusDays(faker.number().numberBetween(0, 30)));

                // Randomly decide if contract should have an end date (some are ongoing)
                if (random.nextBoolean()) {
                    // makes sure endDate is after startDate
                    contract.setEndDate(contract.getStartDate()
                            .plusYears(faker.number().numberBetween(0, 2)));
                } // Leave endDate as null for ongoing contracts

                // Randomly assign an employee
                if (!draftEmployees.isEmpty()) {
                    Employee randomEmployee = draftEmployees.get(random.nextInt(numOfEmployees));
                    contract.setEmployee(randomEmployee);
                }

                draftContracts.add(contract);
            }
        }

        // save final draft lists to repos
        this.employeeRepository.saveAll(draftEmployees);
        this.contractRepository.saveAll(draftContracts);

        // Log number of records created
        System.out.println("employees: " + employeeRepository.count());
        System.out.println("contracts: " + contractRepository.count());
    }

}
