package io.carrie.employee.contract;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.carrie.employee.contract.Contract.ContractType;
import io.carrie.employee.contract.Contract.Department;
import io.carrie.employee.contract.dtos.CreateContractDTO;
import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import io.carrie.employee.employee.EmployeeEndToEndTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContractEndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private ContractRepository contractRepository;
    private ArrayList<Contract> contractList = new ArrayList<>();
    private HashMap<String, String> contractDto = new HashMap<>();

    @BeforeEach // set up data and save in db
    // create some employees for testing:
    setUpEmployees();

    @BeforeEach // set up data and save in db
    public void setUp() {
        RestAssured.port = this.port;

        // clear the data in mock DB before each test
        this.contractRepository.deleteAll();
        this.contractList.clear();

        // create some contracts for testing:
        BigDecimal salary1 = new BigDecimal("65000.00");
        LocalDate startDate1 = LocalDate.of(2007, 5, 15);
        LocalDate endDate1 = LocalDate.of(2013, 5, 15);

        Contract contract1 = new Contract(employee1, Department.MARKETING, ContractType.PART_TIME,
                salary1, 20, startDate1, endDate1);
        this.contractRepository.save(contract1);
        this.contractList.add(contract1);

        Contract contract2 = new Contract(employee2, Department.SALES, ContractType.FULL_TIME, salary1,
                38, startDate1, endDate1);
        this.contractRepository.save(contract2);
        this.contractList.add(contract2);

    }

    // test framework (AAA)
    // arrange - set up mock data and variables
    // act - run the code you are testing
    // assert - check result is correct

    @Nested
    @DisplayName("GET /contracts")
    class GetContractsTests {

        @Test
        public void getAllContracts_ContractsInDB_ReturnsSuccess() {
            given()
                    .when().get("/contracts")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("$", hasSize(2)) // should return 2 records in list
                    .body(matchesJsonSchemaInClasspath("schemas/contract-list-schema.json"));
        }

        @Test
        public void getAllContracts_NoContractsInDB_ReturnsSuccessAndEmptyArray() {
            contractRepository.deleteAll();
            given()
                    .when().get("/contracts")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("$", hasSize(0));
        }

    }

    @Nested
    @DisplayName("GET /contracts/{id}")
    class GetContractByIdTests {

        @Test
        public void getContractById_ContractInDB_ReturnsSuccess() {
            Integer existingId = contractList.get(0).getId();
            given()
                    .when().get("/contracts/" + existingId)
                    .then().statusCode(HttpStatus.OK.value());
        }

        @Test
        public void getContractById_ContractInDB_ReturnsCorrectData() {
            Integer existingId = contractList.get(1).getId();
            given()
                    .when().get("/contracts/" + existingId)
                    .then().statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(existingId))
                    .body("firstName", equalTo("Wanda"))
                    .body("lastName", equalTo("Cosmo"))
                    .body("email", equalTo("wanda.cosmo@example.com"))
                    .body(matchesJsonSchemaInClasspath("schemas/contract-schema.json"));
        }

        @Test
        public void getContractById_ContractNotInDB_NotFound() {
            given()
                    .when().get("/contracts/999")
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void getContractById_IdNotValid_BadRequest() {
            given()
                    .when().get("/contracts/sfee23")
                    .then().statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

    @Nested
    @DisplayName("DELETE /contracts/{id}")
    class DeleteContractByIdTests {

        @Test
        public void deleteContractById_ContractInDb_SuccessNoContent() {
            Integer existingId = contractList.get(0).getId();
            given()
                    .when().delete("contracts/" + existingId)
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
            // todo - id must be double-checked with data
            // todo - check record was actually deleted from repo
        }

        @Test
        public void deleteContractById_ContractNotInDB_NotFound() {
            given()
                    .when().delete("/contracts/999")
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void deleteContractById_IdNotValid_BadRequest() {
            given()
                    .when().delete("/contracts/sfee23")
                    .then().statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

    protected void assertBadRequest(Method httpMethod, String endpoint,
            HashMap<String, String> data) {
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .request(httpMethod, endpoint)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Nested
    @DisplayName("POST /contracts")
    class CreateContractByIdTests {

        private void assertPost(HashMap<String, String> contractDto) {
            assertBadRequest(Method.POST, "/contracts", contractDto);
        }

        @Test
        public void create_ValidData_Created() {

            CreateContractDTO validDto = new CreateContractDTO(
                    1,
                    "SALES",
                    "FULL_TIME",
                    75000.50,
                    38,
                    "2025-01-01",
                    "2026-01-01");

            given()
                    .contentType(ContentType.JSON)
                    .body(validDto)
                    .when()
                    .post("/contracts")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("department", equalTo("SALES")) // returns correct data?
                    .body("contractType", equalTo("FULL_TIME"));
        }

        @Test
        public void create_MissingData_BadRequest() {
            HashMap<String, String> invalidContractDto = new HashMap<>();
            invalidContractDto.put("department", "SALESSSSSS");
            assertPost(invalidContractDto);
        }

        @Test
        public void create_InvalidPhone_BadRequest() {
            contractDto.put("phone", "not-a-phone-number");
            assertPost(contractDto);
            contractDto.put("phone", "12345678901234567890"); // too long
            assertPost(contractDto);
            contractDto.put("phone", "1234567"); // too short
            assertPost(contractDto);
        }

        @Test
        public void create_InvalidDepartment_BadRequest() {
            contractDto.put("department", "invalid-department");
            assertPost(contractDto);
        }

        @Test
        public void create_InvalidAddress_BadRequest() {
            contractDto.put("address", "a");
            assertPost(contractDto);
        }
    }

}
