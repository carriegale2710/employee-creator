# Employee Creator Backend - Spring API

<!-- [![Spring App EC2 Deploy](https://github.com/carriegale2710/employee-creator/actions/workflows/spring-ec2-deploy.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/spring-ec2-deploy.yml) -->

[![Spring Boot Tests](https://github.com/carriegale2710/employee-creator/actions/workflows/spring-boot-test.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/spring-boot-test.yml) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Documentation - Note

This documentation for this project is split up into backend vs front-end specific locations in the code base. They include more details like build steps, testing and change logs.

| Location                                        | Purpose                                                 |
| ----------------------------------------------- | ------------------------------------------------------- |
| [`README.md`](../README.md)                     | 🔷 High-level overview of the entire full-stack project |
| `employee/README.md` (this file)                | ⚙️ Backend-specific setup, API docs, DB, test config.   |
| [`front-end/README.md`](../front-end/README.md) | 🎨 Frontend-specific setup, dev commands, tests         |

## Build Steps - Backend Setup

<!-- - how to build / run project
- use proper code snippets if there are any commands to run -->

### Installation + Setup

```bash
# Clone the repo
git clone https://github.com/your-username/employee-creator.git
cd employee-creator

# Backend setup
./mvnw spring-boot:run

# Frontend setup
cd frontend
npm install
npm run dev

```

### Running the App Locally (macOS & Windows)

---

#### 🐬 1. Install MySQL

- **macOS**

  ```bash
  brew install mysql
  brew services start mysql
  ```

- **Windows**

  1. Download [MySQL Installer](https://dev.mysql.com/downloads/installer/)
  2. Install MySQL Server
  3. Make sure it's running as a service

---

#### 🛠️ 2. Create Database

Open MySQL shell:

```bash
mysql -u root -p
```

Then run:

```sql
CREATE DATABASE your_database_name;
```

> ✅ **Skip user creation if you're using `root` for local dev**
> Otherwise:
>
> ```sql
> CREATE USER 'your_user'@'localhost' IDENTIFIED BY 'your_password';
> GRANT ALL PRIVILEGES ON your_database_name.* TO 'your_user'@'localhost';
> FLUSH PRIVILEGES;
> ```

---

#### 📄 3. Add `.env` File

Create a `.env` file in the project root:

```env
DB_NAME=your_database_name
MYSQL_USER=your_user_or_root
MYSQL_PASS=your_password
```

---

#### 🧯 4. Fix Socket Errors [macOS Only]

If you get MySQL socket errors on Mac, force TCP:

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/${DB_NAME}
```

---

#### ▶️ 5. Run the App

```bash
./mvnw spring-boot:run
```

Or use your IDE (IntelliJ, VSCode, etc.)

---

### Endpoints

Once running, your API will be available at:

```text
http://localhost:8080
```

Opening this in your browser should return "Hello, world".
Use [Postman](https://www.postman.com/downloads/) or a browser (for GET requests) to test.

- **GET all employees:**
  `http://localhost:8080/api/employees`

- **GET single employee:**
  `http://localhost:8080/api/employees/{id}`

- **POST new employee:**

  - Method: `POST`
  - URL: `http://localhost:8080/api/employees`
  - Body (JSON):

    ```json
    {
      "firstName": "Timmy",
      "lastName": "Turner",
      "email": "timmy@fairy.com"
    }
    ```

## Testing

| Type       | Tools Used       | Status |
| ---------- | ---------------- | ------ |
| Unit Tests | JUnit + Mockito  | ✅     |
| E2E Tests  | REST Assured, H2 | ⏳     |

```bash
./mvnw test      # backend

```

## Backend Design Goals / Approach

### Backend decisions

- Included a contracts and departments table with a `one-to-many relationship` for `employees -> contracts` and `departments -> contracts`
- This allows for flexible, quicker UX when updating of DB records via in FE client app with only minor updates eg. salary, contract dates etc.
- Schema, with a one-to-many relationship with each employee (one employee can have many contracts)

## Features

### API Endpoints

| ID  | Method   | Endpoint         | Input                           | Output Data | Success Response |
| --- | -------- | ---------------- | ------------------------------- | ----------- | ---------------- |
| 1   | `GET`    | `/employees`     | none                            | DB List     | `200 OK`         |
| 2   | `GET`    | `/employees/:id` | employee id                     | DB List     | `200 OK`         |
| 3   | `POST`   | `/employees`     | createEmployeeDTO               | DB Record   | `201 Created`    |
| 4   | `DELETE` | `/employees/:id` | employee id                     | No Content  | `204 No Content` |
| 5   | `PATCH`  | `/employees/:id` | employee id + updateEmployeeDTO | DB Record   | `200 OK`         |

### Sample Employee List Response (GET `/employees`)

Joining the employee with their current contract and department for display.

JSON HTTP Response:

```json
[
  {
    "id": 1,
    "firstName": "Timmy",
    "lastName": "Turner",
    "email": "timmehhh@example.com",
    "currentContract": {
      //virtual field
      "contractType": "FULL_TIME",
      "startDate": "2023-01-10",
      "department": "ENGINEERING"
    }
  },
  {
    "id": 2,
    "firstName": "Cosmo",
    "lastName": "Cosma",
    "email": "cosmo@example.com",
    "currentContract": {
      //virtual field
      "contractType": "PART_TIME",
      "startDate": "2022-04-12",
      "department": "HUMAN_RESOURCES"
    }
  }
]
```

[Employees](assets/diagrams/sequence/sequence-diagram.md)

```mermaid

---
config:
  theme: redux-dark-color
---
sequenceDiagram
  actor User
  participant ReactApp as React App
  participant SpringAPI as Spring Boot API
  participant MySQL as MySQL Database
  Note over User: View all employees
  User->>ReactApp: Opens Employee List
  ReactApp->>SpringAPI: GET /employees
  SpringAPI->>MySQL: SELECT * FROM employees
  MySQL-->>SpringAPI: Rows (employee list)
  SpringAPI-->>ReactApp: JSON response
  ReactApp-->>User: Display list
  Note over User: Add a new employee
  User->>ReactApp: Fills out form
  ReactApp->>SpringAPI: POST /employees (form data)
  SpringAPI->>MySQL: INSERT INTO employees
  MySQL-->>SpringAPI: OK
  SpringAPI-->>ReactApp: New employee JSON
  ReactApp-->>User: Confirmation
  Note over User: Edit an employee
  User->>ReactApp: Clicks Edit
  ReactApp->>SpringAPI: PUT /employees/:id (updated data)
  SpringAPI->>MySQL: UPDATE employees WHERE id=...
  MySQL-->>SpringAPI: OK
  SpringAPI-->>ReactApp: Updated JSON
  ReactApp-->>User: Show updated data
  Note over User: Delete an employee
  User->>ReactApp: Clicks Delete
  ReactApp->>SpringAPI: DELETE /employees/:id
  SpringAPI->>MySQL: DELETE FROM employees WHERE id=...
  MySQL-->>SpringAPI: OK
  SpringAPI-->>ReactApp: 200 OK
  ReactApp-->>User: Item removed


```

## Database structure

| What                       | Relationship            | Why                                                                |
| -------------------------- | ----------------------- | ------------------------------------------------------------------ |
| **Employees table**        | Has personal info only  | Department can change → so not stored here                         |
| **Contracts table**        | Has job info per period | Stores department, salary, hours, type, dates for each role/period |
| **Departments table**      | Lookup table            | Keeps department names unique and consistent                       |
| **Employee → Contracts**   | One-to-Many             | - Each employee can have multiple contracts over time              |
| **Department → Contracts** | One-to-Many             | Each contract is tied to one department at that time               |

<!-- - In `Employee`:`@OneToMany(mappedBy = "employee")`
- In `Contract`:`@ManyToOne`
- In `Contract`:`@ManyToOne`
- In `Department`:`@OneToMany(mappedBy = "department")` (optional) -->

[ERD Diagram (DBML)](assets/diagrams/erd/erd.md)
![diagram of one-to-many class between employee and contracts tables in database](assets/diagrams/erd/erd.png)

---

### Schemas

#### Employee Schema

Basic employee personal data — no department or job info here (now handled via `contracts` and `departments`).

```ts
interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string;
  address?: string;
}
```

```json
{
  "id": 1,
  "firstName": "Timmy",
  "lastName": "Turner",
  "email": "timmehhh@example.com",
  "phone": "0400000000",
  "address": "123 Fairy Lane"
}
```

---

#### Contract Schema

Each employee can have multiple contracts across different time periods and departments.

```ts
interface Contract {
  id: number;
  employee: Employee;
  department?: string; //
  contractType: string; //eg. "FULL_TIME" | "PART_TIME";
  salaryAmount?: number;
  hoursPerWeek?: number;
  startDate: string; // ISO date string
  endDate?: string; // ISO date string or null
  isActive: boolean; //virtual field
}
```

```json
{
  "id": 1,
  "employee": timmyTurner,
  "department": "Talent",
  "contractType": "FULL_TIME",
  "salaryAmount": 80000,
  "hoursPerWeek": 38,
  "startDate": "2023-01-10",
  "endDate": null,
  "isActive": true //virtual field
}
```

#### Department Schema

Lookup table to keep departments consistent but flexible to be updated later.

```ts
interface Department {
  id: number;
  name: "ENGINEERING" | "SALES" | "HUMAN_RESOURCES" | "MARKETING" | "FINANCE";
}
```

JSON HTTP 'PATCH' Request:

```json
{
  "id": 1, //not needed if "POST" request (creation)
  "name": "ENGINEERING"
}
```

---

### DTO Schemas

#### CreateEmployeeDTO

When creating a new employee with an option for initial contract:

```ts
interface CreateEmployeeDTO {
  firstName: string;
  lastName: string;
  email: string; //get backend to generate if empty?
  phone: string;
  address?: string;
  contract?: ContractDTO; //opt.
}
```

AS `POST` HTTP request (JSON):

```json
{
  "firstName": "Timmy",
  "lastName": "Turner",
  "email": "timmehhh@example.com",
  "phone": "0400000000",
  "currentContract": CreateContractDTO //opt
}
```

#### UpdateEmployeeDTO

When updating personal details of existing employee.

- As `PATCH` HTTP request to update only inputed fields (JSON):

```json
{
  "firstName": "Timmy", //opt.
  "lastName": "Turner", //opt.
  "email": "timmy_turner@example.com" //opt.
  "phone": "0400000000", //opt.
}
```

#### CreateContractDTO

```ts
interface CreateContractDTO {
  departmentId: number; //get this from name
  contractType: "FULL_TIME" | "PART_TIME";
  startDate: string; // ISO date string
  salaryAmount?: number;
  hoursPerWeek?: number;
}
```

```json
{
  "contractType": "FULL_TIME",
  "startDate": "2023-01-10",
  "department": "ENGINEERING"
}
```

#### UpdateContractDTO (bonus)

When updating personal details of existing contract.

- As `PATCH` HTTP request to update only salary in employee's contract (JSON):

```json
{
  "employee_id": 11, //required
  "contract_id": 101, //required
  "salaryAmount": 85000
}
```

---

---

## Change logs

### 05/07/2025 - Backend API Project Setup

- Installed SpringBoot and dependencies
- Setup application.properties file for mySQL
- Decided on SQL schema for employee database
- Defining data types for employees table in `assets/data/README.md`
- Linking to SQL and testing table database syncing

### 06/07/2025 - 07/07/2025 Backend API Testing setup

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

### 08/07/2025 - Basic CRUD endpoints + Writing tests

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

### 10/07/2025 - Spring App EC2 Deployment

- fixed bug with env variables not loading into application.properties for backend by changing file name
- Successfully deployed backend Spring App to EC2 instance in AWS - Available at: `ec2-3-107-209-102.ap-southeast-2.compute.amazonaws.com:8080/employees`

### 12/07/2025 - Custom Domain for API

- Created new healthcheck controller endpoint for testing load balancer

- Bought custom domain from porkbun - to be implemented:
- Started: Auto-deployment workflow for Spring App EC2 Deployment (not working yet) (on hold)

### 14/07/2025 - API Logging

- API logging strategy:
  - implemented SLF4J facade in EmployeeApplication controller class
  - created dev profile for console and file log formats and level filtering
  - configured SLF4J to use Log4j2 instead of default (Logback)
  - created log4j2-spring.xml for log configuration and formatting, with dev profile

### 19/07/2025 - Error handling

- went back and introduced error handling for backend API

`Delete employee record`:

- Return `BAD REQUEST` if wrong input - fixed, passing
- Return `NO_CONTENT` on successful deletion - fixed, passing

`Create employee record`:

- Return `BAD REQUEST` if invalid first/last name - passing
- Return `BAD REQUEST` if invalid duplicate email - passing

### 20/07/2025

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

## Agile Board (Backend)

### In progress

Contract: (one-to-many relationship: employee can have multiple contracts)

- contract entity
- contract controller
- contract service
- contract repository
- createContractDTO
- updateContractDTO
- implement any custom errors/utils
- error handling

### Sprint

- prepare data handling on backend to make front-end just an IO (goal: reduce front-end complexity)

### Backlog

- (bonus) Use Google API to validate and search address formats

## QA Checklist

### 🔧 Backend (Spring Boot)

- [x] App compiles and runs
- [x] Logging strategy in place
- [ ] API has working CRUD endpoints (GET, CREATE, DELETE + EDIT)

  - [x] employees
  - [ ] contracts
  - [ ] departments

- [x] Unit + end-to-end tests (JUnit, Mockito)

  - [x] employees
  - [ ] contracts
  - [ ] departments

- [ ] Error handling implemented
  - [x] employees
  - [ ] contracts
  - [ ] departments

### 🚚 Delivery & Deployment

- [x] README includes clear setup steps for both API and Web app (local dev)
- [x] App is production-ready
- [ ] Hosting link works (fix custom domain)
- [ ] Bug-free and everything compiles + runs as expected
- [ ] Codebase is understandable and maintainable
- [ ] Code is clean + well documented

## Licensing Details

<!-- What type of license are you releasing this under? -->

MIT License.

---

## Related projects, reimplementations, misc

### Client App UI

See related documentation for [React Client App](front-end/README.md).
