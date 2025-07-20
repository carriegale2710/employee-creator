package io.carrie.employee.employee;

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

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeEndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private HashMap<String, String> employeeDto = new HashMap<>();

    @BeforeEach // set up data and save in db
    public void setUp() {
        RestAssured.port = this.port;

        // clear the data in mock DB before each test
        this.employeeRepository.deleteAll();
        this.employeeList.clear();

        // create some employees for testing:
        Employee employee1 = new Employee("Timmy", "Turner", "timmehhh@example.com");
        this.employeeRepository.save(employee1);
        this.employeeList.add(employee1);

        Employee employee2 = new Employee("Wanda", "Cosmo", "wanda.cosmo@example.com");
        this.employeeRepository.save(employee2);
        this.employeeList.add(employee2);

        this.employeeDto.put("firstName", "Cosmo");
        this.employeeDto.put("lastName", "Cosma");
        this.employeeDto.put("email", "cosmo@example.com");
    }

    // test framework (AAA)
    // arrange - set up mock data and variables
    // act - run the code you are testing
    // assert - check result is correct

    @Nested
    @DisplayName("GET /employees")
    class GetEmployeesTests {

        @Test
        public void getAllEmployees_EmployeesInDB_ReturnsSuccess() {
            given()
                    .when().get("/employees")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("$", hasSize(2)) // should return 2 records in list
                    .body(matchesJsonSchemaInClasspath("schemas/employee-list-schema.json"));
        }

        @Test
        public void getAllEmployees_NoEmployeesInDB_ReturnsSuccessAndEmptyArray() {
            employeeRepository.deleteAll();
            given()
                    .when().get("/employees")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("$", hasSize(0));
        }

    }

    @Nested
    @DisplayName("GET /employees/{id}")
    class GetEmployeeByIdTests {

        @Test
        public void getEmployeeById_EmployeeInDB_ReturnsSuccess() {
            Integer existingId = employeeList.get(0).getId();
            given()
                    .when().get("/employees/" + existingId)
                    .then().statusCode(HttpStatus.OK.value());
        }

        @Test
        public void getEmployeeById_EmployeeInDB_ReturnsCorrectData() {
            Integer existingId = employeeList.get(1).getId();
            given()
                    .when().get("/employees/" + existingId)
                    .then().statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(existingId))
                    .body("firstName", equalTo("Wanda"))
                    .body("lastName", equalTo("Cosmo"))
                    .body("email", equalTo("wanda.cosmo@example.com"))
                    .body(matchesJsonSchemaInClasspath("schemas/employee-schema.json"));
        }

        @Test
        public void getEmployeeById_EmployeeNotInDB_NotFound() {
            given()
                    .when().get("/employees/999")
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void getEmployeeById_IdNotValid_BadRequest() {
            given()
                    .when().get("/employees/sfee23")
                    .then().statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

    @Nested
    @DisplayName("DELETE /employees/{id}")
    class DeleteEmployeeByIdTests {

        @Test
        public void deleteEmployeeById_EmployeeInDb_SuccessNoContent() {
            Integer existingId = employeeList.get(0).getId();
            given()
                    .when().delete("employees/" + existingId)
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
            // todo - id must be double-checked with data
            // todo - check record was actually deleted from repo
        }

        @Test
        public void deleteEmployeeById_EmployeeNotInDB_NotFound() {
            given()
                    .when().delete("/employees/999")
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void deleteEmployeeById_IdNotValid_BadRequest() {
            given()
                    .when().delete("/employees/sfee23")
                    .then().statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

    protected void assertBadRequest(Method httpMethod, String endpoint, HashMap<String, String> data) {
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .request(httpMethod, endpoint)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Nested
    @DisplayName("POST /employees")
    class CreateEmployeeByIdTests {

        private void assertPost(HashMap<String, String> employeeDto) {
            assertBadRequest(Method.POST, "/employees", employeeDto);
        }

        @Test
        public void create_ValidData_Created() {
            given()
                    .contentType(ContentType.JSON)
                    .body(employeeDto)
                    .when()
                    .post("/employees")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("firstName", equalTo("Cosmo")); // returns correct data?
        }

        @Test
        public void create_MissingData_BadRequest() {
            HashMap<String, String> invalidEmployeeDto = new HashMap<>();
            invalidEmployeeDto.put("firstName", "Cosmo");
            assertPost(invalidEmployeeDto);
        }

        @Test
        public void create_InvalidFirstName_BadRequest() {
            employeeDto.put("lastName", "hasPunctuation,,.");
            assertPost(employeeDto);
            employeeDto.put("lastName", "hasNumbers3245");
            assertPost(employeeDto);
            employeeDto.put("lastName", "thisisareallyreallyreallylongnamethatistoolong");
            assertPost(employeeDto);
        }

        @Test
        public void create_InvalidLastName_BadRequest() {
            employeeDto.put("lastName", "hasPunctuation,,.");
            assertPost(employeeDto);
            employeeDto.put("lastName", "hasNumbers3245");
            assertPost(employeeDto);
            employeeDto.put("lastName", "thisisareallyreallyreallylongnamethatistoolong");
            assertPost(employeeDto);
        }

        @Test
        public void create_InvalidEmail_BadRequest() {
            employeeDto.put("email", "cosmoexample.com");
            assertPost(employeeDto);
        }

        @Test
        public void create_DuplicateEmail_BadRequest() {
            // NOTE - this should not be 500 internal server error but 400!
            employeeDto.put("email", "timmehhh@example.com"); // only email matters
            assertPost(employeeDto);
        }
    }

    // SECTION - PATCH "/employees/{id}"
    // (on hold - for future editing features)

    @Nested
    @DisplayName("PATCH /employees/{id}")
    class UpdateEmployeeByIdTests {

        private void assertPatch(HashMap<String, String> employeeDto) {
            assertBadRequest(Method.PATCH, "/employees/1", employeeDto);
        }

        @Test
        public void patchById_ValidData_Created() {
            given()
                    .contentType(ContentType.JSON)
                    .body(employeeDto)
                    .when()
                    .patch("/employees/1")
                    .then()
                    .statusCode(HttpStatus.OK.value());
            // .body("firstName", equalTo("Cosmo")); // returns correct data
            // // todo - check data was successfully added as new employee in DB
        }

        @Test
        public void patchById_InvalidFirstName_BadRequest() {
            employeeDto.put("lastName", "hasPunctuation,,.");
            assertPatch(employeeDto);
            employeeDto.put("lastName", "hasNumbers3245");
            assertPatch(employeeDto);
            employeeDto.put("lastName", "thisisareallyreallyreallylongnamethatistoolong");
            assertPatch(employeeDto);
        }

        @Test
        public void patchById_InvalidLastName_BadRequest() {
            employeeDto.put("lastName", "hasPunctuation,,.");
            assertPatch(employeeDto);
            employeeDto.put("lastName", "hasNumbers3245");
            assertPatch(employeeDto);
            employeeDto.put("lastName", "thisisareallyreallyreallylongnamethatistoolong");
            assertPatch(employeeDto);
        }

        @Test
        public void patchById_InvalidEmail_BadRequest() {
            employeeDto.put("email", "cosmoexample.com"); // invalid email
            assertPatch(employeeDto);
        }

        @Test
        public void patchById_DuplicateEmail_BadRequest() {
            // NOTE - this should not be 500 internal server error but 400!
            employeeDto.put("email", "timmehhh@example.com");
            assertPatch(employeeDto);
        }
    }
}
