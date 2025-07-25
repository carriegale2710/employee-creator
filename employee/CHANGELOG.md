# Employee Creator Backend - Change Log

## Agile Board

| Status          | Task                    |
| --------------- | ----------------------- |
| **In Progress** |                         |
| **Sprint**      |                         |
| **Backlog**     | - Zod validation <br> - |

### Backlog

- zod validation
- (bonus) Use Google API to validate and search address formats

### Sprint

### In progress

---

## 25/07/2025 - Service Layer Error Handling

- Implemented custom exception handling in service layer
- Created `EmailAlreadyExistsException` for duplicate email validation
- Enhanced `GlobalExceptionHandler` to return proper error messages
- Fixed employee update logic to prevent false positives when updating same employee
- Added `@Transactional` annotation for safe employee deletion with contracts
- Updated service unit tests to include contract repository mocking

---

## 24/07/2025

- deleting an employee AND all associated contracts
- fixed update
- fixed taken email error in form

---

## 23/07/2025

- prepare data handling on backend to make front-end just an IO (goal: reduce front-end complexity)

---

## 22/07/2025

Contract: (one-to-many relationship: employee can have multiple contracts)

- contract entity
- contract controller
- contract service
- contract repository
- createContractDTO
- updateContractDTO
- implement any custom errors/utils
- error handling

---

## 20/07/2025

- Extended employee entity to have more fields (phone number, address)

Edit employee feature:

- updateById PATCH method : used TDD - wrote tests + function in parallel
- (DONE) pass 2x e2e tests for UpdateEmployeeByIdTests :
  (passing individually but not when whole test runs)
  - valid update - getting 404
  - dealing with duplicate emails - getting 404

Contracts feature : (one-to-many relationship: employee can have multiple contracts)

- used employee feature structure as a base to speed things up
- e2e + service tests done

---

## 19/07/2025 - Error handling

- went back and introduced error handling for backend API

`Delete employee record`:

- Return `BAD REQUEST` if wrong input - fixed, passing
- Return `NO_CONTENT` on successful deletion - fixed, passing

`Create employee record`:

- Return `BAD REQUEST` if invalid first/last name - passing
- Return `BAD REQUEST` if invalid duplicate email - passing

---

## 14/07/2025 - API Logging

- API logging strategy:
  - implemented SLF4J facade in EmployeeApplication controller class
  - created dev profile for console and file log formats and level filtering
  - configured SLF4J to use Log4j2 instead of default (Logback)
  - created log4j2-spring.xml for log configuration and formatting, with dev profile

---

## 12/07/2025 - Custom Domain for API

- Created new healthcheck controller endpoint for testing load balancer

- Bought custom domain from porkbun - to be implemented:
- Started: Auto-deployment workflow for Spring App EC2 Deployment (not working yet) (on hold)

---

## 10/07/2025 - Spring App EC2 Deployment

- fixed bug with env variables not loading into application.properties for backend by changing file name
- Successfully deployed backend Spring App to EC2 instance in AWS - Available at: `ec2-3-107-209-102.ap-southeast-2.compute.amazonaws.com:8080/employees`

---

## 08/07/2025 - Basic CRUD endpoints + Writing tests

Created endpoints for:

- `GET` `/employees` = [DB -> list (of records)]
- `GET` `/employees/{id}` = [DB -> record]
- `POST` `/employees` = [DB + record]
- `DELETE` `/employee/{id}` = [DB - record]

API Test Setup:

- Fixed Github workflows for automated testing (see badges at top)
- Outlined e2e test cases and edges for new all endpoints
- Fixed application.properties config bug for test suite
- Created config folder for ModelMapper library - easier to create new entities compared to manual setters
- Created JSON schema for e2e test for creating new employees in DB
- SmokeTest sanity check test
- writing unit tests for Service layer (business logic)

---

## 06/07/2025 - 07/07/2025 Backend API Testing setup

General

- Mapping out endpoints and status codes expected for http requests
- Created Mermaid charts for app layers and core user flows
- Introduction, build steps, testing stack research
- Research on TDD framework
- Cleaning up assets and documentation org//

Backend API

- Created simple HomeController and tested app runs in localhost:8080 browser
- Create Employee entity with getters and setters
- Creates basic employee controller, service, repository classes.
- Write code for basic get method

Backend E2E Test

- Installs `spring-boot-starter-test dependency` which includes JUnit, Mockito, and other useful testing libraries.
- Installs dependency for H2 database for testing with separate db - avoid breaking from actual DB.
- Installs Rest Assured Library for writing automated test that send requests during testing.
- Adds records for e2e test setup using H2 database for mocking with dummy data
  - Creates basic test for `getAll()`
- test badges update - Github actions

---

## 05/07/2025 - Backend API Project Setup

- Installed SpringBoot and dependencies
- Setup application.properties file for mySQL
- Decided on SQL schema for employee database
- Defining data types for employees table in `assets/data/README.md`
- Linking to SQL and testing table database syncing
