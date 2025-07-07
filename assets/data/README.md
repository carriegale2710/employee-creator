### Schemas

### Employee Schema

Data types for properties of Employee class.

- id : unique number (generated in backend)
- first_name : string
- last_name : string
- email : unique string (generated in backend)
- department : enum (dropdown in UI)
- startDate : Date (datepicker UI, validation needed)

### Employee List - Sample Data (GET Request)

This is what the Employee List Data should look like when fetched from API.

```json
[
  {
    "id": 1,
    "firstName": "Timmy",
    "lastName": "Turner",
    "email": "timmehhh@example.com",
    "department": "ENGINEERING",
    "startDate": "2023-01-10"
  },
  {
    "id": 2,
    "firstName": "Cosmo",
    "lastName": "Cosma",
    "email": "cosmo@example.com",
    "department": "HUMAN_RESOURCES",
    "startDate": "2020-08-15"
  },
  {
    "id": 3,
    "firstName": "Wanda",
    "lastName": "Fairywinkle",
    "email": "wanda@example.com",
    "department": "SALES",
    "startDate": "2019-04-22"
  }
]
```

### CreateEmployeeDTO Schema

This is the type of data that will be sent from the client side. Note: A unique Id & email should be generated in backend

- first_name : string
- last_name : string
- department : enum (dropdown in UI)
- startDate : Date (datepicker UI, validation needed)

Note: 🔒 "department" must be one of:
"ENGINEERING", "SALES", "HUMAN_RESOURCES", "MARKETING", "FINANCE"

### Sample Data for POST Request

```json
{
  "firstName": "Timmy",
  "lastName": "Turner",
  "department": "ENGINEERING",
  "startDate": "2023-01-10"
}
```

### SQL Schema - Database

```sql
-- Define enum type
CREATE TYPE department AS ENUM (
  'ENGINEERING',
  'SALES',
  'HUMAN_RESOURCES',
  'MARKETING',
  'FINANCE'
);

-- Table using enum
CREATE TABLE employees (
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  department department NOT NULL,
  start_date DATE NOT NULL
);
```
