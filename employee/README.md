# Backend - Spring Boot API

[![Spring Boot Tests](https://github.com/carriegale2710/employee-creator/actions/workflows/spring-boot-test.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/spring-boot-test.yml) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Quick Setup

```bash
cd employee
./mvnw clean install
./mvnw spring-boot:run  # http://localhost:8080
```

## Database Setup

### Install MySQL

**macOS:**

```bash
brew install mysql
brew services start mysql
```

**Windows:** Download [MySQL Installer](https://dev.mysql.com/downloads/installer/)

### Configure Database

```bash
mysql -u root -p
```

```sql
CREATE DATABASE your_database_name;
```

### Environment Variables

Create `.env` file:

```env
DB_NAME=your_database_name
MYSQL_USER=your_user_or_root
MYSQL_PASS=your_password
```

## Testing

| Type       | Tools Used       | Status |
| ---------- | ---------------- | ------ |
| Unit Tests | JUnit + Mockito  | ✅     |
| E2E Tests  | REST Assured, H2 | ✅     |

```bash
./mvnw test  # JUnit + Mockito + REST Assured
```

## API Endpoints

### Employees

- `GET /employees` - List all employees
- `GET /employees/{id}` - Get employee by ID
- `POST /employees` - Create employee

  - Example Body (JSON):

    ```json
    {
      "firstName": "Timmy",
      "lastName": "Turner",
      "email": "timmy.turner@example.com",
      "phone": "0400000000",
      "address": "123 Fairy Lane" //opt
    }
    ```

- `PATCH /employees/{id}` - Update employee

  - Example Body (JSON):

    ```json
    {
      "employeeId": 15,
      "department": "SALES",
      "contractType": "FULL_TIME",
      "salaryAmount": 80000,
      "hoursPerWeek": 38,
      "startDate": "2023-01-10",
      "endDate": null //opt
    }
    ```

- `DELETE /employees/{id}` - Delete employee

### Contracts

- `GET /contracts` - List all contracts
- `GET /contracts/{id}` - Get contract by ID
- `POST /contracts` - Create contract
- `DELETE /contracts/{id}` - Delete contract

## Database Schema

**Employees** (1) → **Contracts** (Many)

Each employee can have multiple contracts over time. Contracts are immutable legal documents.

![diagram of one-to-many class between employee and contracts tables in database](assets/diagrams/erd/erd.png)

## Tech Details

- **Spring Boot 3** + **Java 21** for enterprise-grade APIs
- **MySQL** + **JPA** for data persistence
- **JUnit 5** + **Mockito** for unit testing
- **REST Assured** + **H2** for integration testing
- **Maven** for dependency management
- **Log4j2** for application logging

## Architecture Notes

- **TDD approach**: Tests written before implementation
- **Entity separation**: Employees vs Contracts for better organization
- **One-to-Many relationship**: Supports contract history tracking
- **Immutable contracts**: No update operations by design

## Related projects, reimplementations, assets

For full project overview, see [main README](../README.md).

### Client App UI

See related documentation for [React Client App](../front-end/README.md).

### Change Log

For detailed development history and feature updates, see [CHANGELOG.md](CHANGELOG.md).

## Sequence Diagram

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
