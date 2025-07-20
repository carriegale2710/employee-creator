package io.carrie.employee.contract;

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
public class ContractEndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ContractRepository contractRepository;
    private ArrayList<Contract> contractList = new ArrayList<>();
    private HashMap<String, String> contractDto = new HashMap<>();

    @BeforeEach // set up data and save in db
    public void setUp() {
        RestAssured.port = this.port;

        // clear the data in mock DB before each test
        this.contractRepository.deleteAll();
        this.contractList.clear();

        // create some contracts for testing:
        Contract contract1 = new Contract();
        this.contractRepository.save(contract1);
        this.contractList.add(contract1);

        Contract contract2 = new Contract();
        this.contractRepository.save(contract2);
        this.contractList.add(contract2);

        this.contractDto.put("firstName", "Cosmo");
        this.contractDto.put("lastName", "Cosma");
        this.contractDto.put("email", "cosmo@example.com");
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
    @DisplayName("POST /contracts")
    class CreateContractByIdTests {

        private void assertPost(HashMap<String, String> contractDto) {
            assertBadRequest(Method.POST, "/contracts", contractDto);
        }

        @Test
        public void create_ValidData_Created() {
            given()
                    .contentType(ContentType.JSON)
                    .body(contractDto)
                    .when()
                    .post("/contracts")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("firstName", equalTo("Cosmo")); // returns correct data?
        }

        @Test
        public void create_MissingData_BadRequest() {
            HashMap<String, String> invalidContractDto = new HashMap<>();
            invalidContractDto.put("firstName", "Cosmo");
            assertPost(invalidContractDto);
        }

        @Test
        public void create_InvalidFirstName_BadRequest() {
            contractDto.put("lastName", "hasPunctuation,,.");
            assertPost(contractDto);
            contractDto.put("lastName", "hasNumbers3245");
            assertPost(contractDto);
            contractDto.put("lastName", "thisisareallyreallyreallylongnamethatistoolong");
            assertPost(contractDto);
        }

        @Test
        public void create_InvalidLastName_BadRequest() {
            contractDto.put("lastName", "hasPunctuation,,.");
            assertPost(contractDto);
            contractDto.put("lastName", "hasNumbers3245");
            assertPost(contractDto);
            contractDto.put("lastName", "thisisareallyreallyreallylongnamethatistoolong");
            assertPost(contractDto);
        }

        @Test
        public void create_InvalidEmail_BadRequest() {
            contractDto.put("email", "cosmoexample.com");
            assertPost(contractDto);
        }

        @Test
        public void create_DuplicateEmail_BadRequest() {
            // NOTE - this should not be 500 internal server error but 400!
            contractDto.put("email", "timmehhh@example.com"); // only email matters
            assertPost(contractDto);
        }
    }

    // SECTION - PATCH "/contracts/{id}"
    // (on hold - for future editing features)

    @Nested
    @DisplayName("PATCH /contracts/{id}")
    class UpdateContractByIdTests {

        @Test
        public void patchById_ValidData_Created() {
            Integer existingId = contractList.get(0).getId();
            given()
                    .contentType(ContentType.JSON)
                    .body(contractDto)
                    .when()
                    .patch("/contracts/" + existingId)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("firstName", equalTo("Cosmo")); // returns correct data
            // todo - check data was successfully added as new contract in DB
        }

        private void assertPatch(HashMap<String, String> contractDto) {
            Integer existingId = contractList.get(0).getId();
            assertBadRequest(Method.PATCH, "/contracts/" + existingId, contractDto);
        }

        @Test
        public void patchById_InvalidFirstName_BadRequest() {
            contractDto.put("lastName", "hasPunctuation,,.");
            assertPatch(contractDto);
            contractDto.put("lastName", "hasNumbers3245");
            assertPatch(contractDto);
            contractDto.put("lastName", "thisisareallyreallyreallylongnamethatistoolong");
            assertPatch(contractDto);
        }

        @Test
        public void patchById_InvalidLastName_BadRequest() {
            contractDto.put("lastName", "hasPunctuation,,.");
            assertPatch(contractDto);
            contractDto.put("lastName", "hasNumbers3245");
            assertPatch(contractDto);
            contractDto.put("lastName", "thisisareallyreallyreallylongnamethatistoolong");
            assertPatch(contractDto);
        }

        @Test
        public void patchById_InvalidEmail_BadRequest() {
            contractDto.put("email", "cosmoexample.com"); // invalid email
            assertPatch(contractDto);

        }

        @Test
        public void patchById_DuplicateEmail_BadRequest() {
            // NOTE - this should not be 500 internal server error but 400!
            contractDto.put("email", "timmehhh@example.com");
            assertPatch(contractDto);
        }

    }
}
