package io.carrie.employee.contract;

import io.carrie.employee.contract.enums.ContractType;
import io.carrie.employee.contract.enums.Department;
import io.carrie.employee.contract.dtos.CreateContractDTO;
import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

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
	private EmployeeRepository employeeRepository;

	@Autowired
	private ContractRepository contractRepository;

	private ArrayList<Employee> employeeList = new ArrayList<>();
	private ArrayList<Contract> contractList = new ArrayList<>();

	private Employee employee1;
	private Employee employee2;

	// SECTION 1. Setup Testing Environment - run before each test

	@BeforeEach
	public void setUpContracts() {
		RestAssured.port = this.port;

		// SECTION 1.1 clear the data in mock DB before each test

		this.employeeRepository.deleteAll();
		this.contractRepository.deleteAll();
		this.employeeList.clear();
		this.contractList.clear();

		// SECTION 1.2 create and save some mock employees

		employee1 = Employee.builder()
				.firstName("Timmy")
				.lastName("Turner")
				.email("timmy.turner@example.com")
				.phone("0412345678")
				.address("123 Main St")
				.build();
		this.employeeRepository.save(employee1);
		this.employeeList.add(employee1);

		employee2 = Employee.builder()
				.firstName("Wanda")
				.lastName("Cosmo")
				.email("wanda.cosmo@example.com")
				.phone("0423456789")
				.address("456 Oak Ave")
				.build();
		this.employeeRepository.save(employee2);
		this.employeeList.add(employee2);

		// SECTION 1.3 create and save some mock contracts for our mock employees

		Contract contract1 = Contract.builder()
				.employee(employee1)
				.department(Department.MARKETING)
				.contractType(ContractType.PART_TIME)
				.salaryAmount(new BigDecimal("65000.00"))
				.hoursPerWeek(20)
				.startDate(LocalDate.of(2007, 5, 15))
				.endDate(LocalDate.of(2013, 5, 15))
				.build();
		this.contractRepository.save(contract1);
		this.contractList.add(contract1);

		Contract contract2 = Contract.builder()
				.employee(employee2)
				.department(Department.SALES)
				.contractType(ContractType.FULL_TIME)
				.salaryAmount(new BigDecimal("65000.00"))
				.hoursPerWeek(38)
				.startDate(LocalDate.of(2007, 5, 15))
				.endDate(LocalDate.of(2013, 5, 15))
				.build();
		this.contractRepository.save(contract2);
		this.contractList.add(contract2);

	}

	// SECTION 2. Helper methods

	private String assertBadRequest(Method httpMethod, String endpoint, Object data) {
		Response response = given()
				.contentType(ContentType.JSON)
				.body(data)
				.when()
				.request(httpMethod, endpoint);
		response.then().statusCode(HttpStatus.BAD_REQUEST.value());
		String error = response.jsonPath().getString("errors.defaultMessage"); // error message
		System.out.println(error);
		return error;
	}

	// SECTION 3. Test cases to run against the API

	// Use the Arrange-Act-Assert pattern to structure your tests:
	// 1. arrange - set up mock data and variables
	// 2. act - run the code you are testing
	// 3. assert - check result is correct

	@Nested
	@DisplayName("GET /contracts")
	class GetContractsTests {

		@Test
		public void getAllContracts_ContractsInDB_ReturnsSuccess() {
			given()
					.when().get("/contracts")
					.then().statusCode(HttpStatus.OK.value())
					.body("$", hasSize(2)) // should return 2 records in list
					.body(matchesJsonSchemaInClasspath("schemas/contracts/contract-list-schema.json"));
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
					.body("department", equalTo("SALES"))
					.body("contractType", equalTo("FULL_TIME"))
					.body("hoursPerWeek", equalTo(38))
					.body(matchesJsonSchemaInClasspath("schemas/contracts/contract-schema.json"));
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
					.when().delete("/contracts/" + existingId)
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

	@Nested
	@DisplayName("POST /contracts")
	class CreateContractByIdTests {

		private CreateContractDTO createContractDto() {
			// factory method - fresh instance each time
			// uses different data from the one in setUpContracts()
			return new CreateContractDTO(
					employee1.getId(),
					"DESIGN",
					"CASUAL",
					new BigDecimal("65000"),
					20,
					"2024-01-01",
					"2025-01-01");
		}

		private String assertBadRequestPost(CreateContractDTO contractDto) {
			return assertBadRequest(Method.POST, "/contracts", contractDto);
		}

		@Test
		public void create_ValidData_Created() {
			CreateContractDTO contractDto = createContractDto();

			given()
					.contentType(ContentType.JSON).body(contractDto)
					.when().post("/contracts")
					.then().statusCode(HttpStatus.CREATED.value());

		}

		@Test
		public void create_ValidData_CreatedAndReturnsCorrectData() {
			CreateContractDTO contractDto = createContractDto();

			Response response = given()
					.contentType(ContentType.JSON).body(contractDto)
					.when().post("/contracts");

			response.then()
					.statusCode(HttpStatus.CREATED.value())
					// has correct schema
					.body(matchesJsonSchemaInClasspath("schemas/contracts/contract-schema.json"))
					// contains the correct data
					.body("department", equalTo("DESIGN"))
					.body("contractType", equalTo("CASUAL"));

		}

		@Test
		public void create_ValidData_CreatedAndSavedInDb() {
			CreateContractDTO contractDto = createContractDto();

			Response response = given()
					.contentType(ContentType.JSON).body(contractDto)
					.when().post("/contracts");

			response.then()
					.statusCode(HttpStatus.CREATED.value());

			// Check the contract was actually saved in the repository
			Contract createdContract = contractRepository.findById(response.jsonPath().getInt("id")).orElse(null);
			Assertions.assertNotNull(createdContract, "Contract should be saved in the repository");
			// check data of created contract matches with DTO we sent in
			Assertions.assertEquals(contractDto.getDepartment(), createdContract.getDepartment().name());
			Assertions.assertEquals(contractDto.getContractType(), createdContract.getContractType().name());

		}

		@Test
		public void create_MissingData_BadRequest() {
			CreateContractDTO contractDto = createContractDto();
			contractDto.setEmployeeId(null); // Missing employeeId
			assertBadRequestPost(contractDto);
		}

		@Test
		public void create_InvalidDepartment_BadRequest() {
			CreateContractDTO contractDto = createContractDto();
			contractDto.setDepartment("INVALID DEPARTMENT");
			assertBadRequestPost(contractDto);
		}

		@Test
		public void create_InvalidSalary_BadRequest() {
			CreateContractDTO contractDto = createContractDto();
			contractDto.setSalaryAmount(new BigDecimal("-1000")); // negative salary
			assertBadRequestPost(contractDto);
		}

		@Test
		public void create_InvalidContractType_BadRequest() {
			CreateContractDTO contractDto = createContractDto();
			contractDto.setContractType("INVALID CONTRACT TYPE");
			assertBadRequestPost(contractDto);
		}

		@Test
		public void create_InvalidHoursPerWeek_BadRequest() {
			CreateContractDTO contractDto = createContractDto();
			contractDto.setHoursPerWeek(55); // exceeds max 50
			assertBadRequestPost(contractDto);
		}
	}

}
