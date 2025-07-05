# Employee Creator App

## Employees Database Design

### CreateEmployee DTO

This is the type of data that will be sent from the client side

```json
{
  "first_name": "Timmy",
  "last_name": "Turner",
  "email": "timmehhh@example.com:",
  "job_title": "Junior Wish Coordinator"
}
```

A unique Id should be generated in backend

### Employee Class

- id : int (must be unique)
- first_name : String
- last_name : String
- email : string (must be unique)
- job_title : string

### JSON Dummy Data

```json
[
  {
    "id": 1,
    "first_name": "Timmy",
    "last_name": "Turner",
    "email": "timmehhh@example.com",
    "job_title": "Junior Wish Coordinator"
  },
  {
    "id": 2,
    "first_name": "Cosmo",
    "last_name": "Wanda",
    "email": "cosmo@example.com",
    "job_title": "Chaos Engineer"
  },
  {
    "id": 3,
    "first_name": "Wanda",
    "last_name": "Fairywinkle",
    "email": "wanda@example.com",
    "job_title": "Senior Wish Strategist"
  }
]
```

### SQL SCHEMA CREATION

```sql
CREATE TABLE employees (
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  job_title VARCHAR(100),
);
```
