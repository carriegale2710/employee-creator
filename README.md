# Employee Creator

{add test badges here, all projects you build from here on out will have tests, therefore you should have github workflow badges at the top of your repositories: [Github Workflow Badges](https://docs.github.com/en/actions/monitoring-and-troubleshooting-workflows/adding-a-workflow-status-badge)}

[![CI](https://github.com/carriegale2710/employee-creator/actions/workflows/main.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/main.yml)

## Demo & Snippets

- Include hosted link
- Include images of app if CLI or Client App

---

## Requirements / Purpose

### Purpose of project

Demonstrate understanding of writing RESTful APIs.

### Techstack and why

1. Backend:

   - Java: using OOP is ideal for defining Employee classes with interfaces - can be extended upon.
   - Spring: Easier to manage dependency injection and database updates for RESTful APIs.

2. Frontend:
   - React: SPA for responsive layout
   - Typescript: validating forms for user input before being sent to DB saves time in both DX and UX.
   - SCSS/Tailwind: For modular, reusable styles and themes based on design systems.

### MVP - Project Specification

[Project Brief](https://github.com/nology-tech/aus-post-course-guide/tree/main/projects/employee-creator)

- We need a web application to manage employees:
  - [ ] create
  - [ ] list
  - [ ] modify
  - [ ] delete
- The schema for the employee is left to the criteria of the candidate.

Also code should be:

- [ ] Production ready.
- [ ] Understandable and maintainable by other developers.
- [ ] Robust and handle error situations.
- [ ] Bug free, compile and work.
- [ ] Include instructions to compile and run the API and the Web app in localhost.
- [ ] Hosting (Heroku, AWS, Azure, etc.) is required.
- [ ] If your code includes unit tests you may use a unit test framework of your choice.

## Hints

### React hints

- React Redux is recommended.
  - You may want to use a Redux middlewear like thunks or epics to handle async actions
- Typescript is recommended.
- React hooks are recommended.
- You can include any other open source NPM library.
- Feel free to use your favorite CSS framework.
  - Tailwind is a good choice
- Feel free to use your favorite middleware.
- Add some basic validations on the form like:
  - [ ] input required
  - [ ] input max length
- Reccomended:
  - [ ] React Hook Form
  - [ ] Zod are reccomended
- The site should be responsive.
  - [ ] media queries
  - [ ] flexbox

### RESTful API hints

- The list can be a local database
  - [x] MySQL is reccomended
- Implementing an API logging strategy.
  - [ ] Log2j **highly** reccomended
- [ ] Implementing error handling strategy.
- At least 3 endpoints are required:
  - [ ] To create an employee `/create`
  - [ ] To get a list of existing employees `/list`
  - [ ] To delete an employee `delete?id={id}`

## Design Snippets

### Basic UI Example

![Employee List Page](assets/UI/employee-list.PNG)
![Employee Edit / Create Form 1](assets/UI/form-part-1.PNG)
![Employee Edit / Create Form 2](assets/UI/form-part-2.PNG)

### Technology / Documentation Recommendations

#### Frontend

- [ ] Vite with `react-ts`
- [ ] Use SCSS instead of CSS, `npm install sass`
  - [ ] Or just use Tailwind
- [ ] React Form Hook for form validation & submission
  - https://react-hook-form.com/get-started
- [ ] Typescript React Cheatsheet
  - https://react-typescript-cheatsheet.netlify.app/docs/basic/getting-started/basic_type_exampleReact Cheat Sheet
- [ ] React Router for routes
  - https://reactrouter.com/en/main/start/tutorial

#### Backend

- Dependencies

  - [x] Spring Web,
  - [x] Validation I/O
  - [ ] Spring Testing
    - [ ] Rest assured
    - [ ] mockito
  - [x] Spring Data JPA
  - [x] MySQL Driver
  - [x] Spring Devtools

- [x] application.properties starter:

```
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.username=root
spring.datasource.password=MyPass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.generate-ddl=true
```

Testing

- https://spring.io/guides/gs/testing-web/
- https://www.baeldung.com/spring-mock-rest-template

Logging

- https://www.baeldung.com/spring-boot-logging

CORS Errors

- https://www.baeldung.com/spring-cors

API Semantics

- https://www.uniprot.org/help/rest-api-headers
- https://restfulapi.net/resource-naming/

---

## Build Steps

- how to build / run project
- use proper code snippets if there are any commands to run

---

## Design Goals / Approach

1. Design goals:

- Understandable and maintainable by other developers.
- Robust and handle error situations.

- layers:
  ![service-design](assets/refs/layer-example.png)

- Use a top-down approach for backend using TDD framework -> write up basic tests before coding to understand functionality, entity shapes & edge cases.

2. Why did you implement this the way you did?

---

## Features

- What features does the project have?
- list them...

### MVP

1. Create an employee

- POST
- input: formData -> CreateEmployeeDTO

2. Delete employee from DB

- DELETE (or PUT if soft delete)
- input : employee id

3. Edit employee details

- PUT
- /id/edit
- formData -> CreateEmployeeDTO

4. Employee List

- GET (for all)
- list/page/search

### BONUS

5. Find employee by id

- GET
- /id

6. Search Bar (find by name match)

- GET
- /name

---

## Known issues

- Remaining bugs, things that have been left unfixed
- Features that are buggy / flimsy

---

## Future Goals

- What are the immediate features you'd add given more time / ideas parking lot:

---

## Change logs

- Write a paragraph labelled with the date every day you work on the project to discuss what you've done for the say. Be specific about the changes that have happened for that day.

### 05/07/2025 - Backend Project Setup

- Installed SpringBoot and dependencies
- Setup application.properties file for mySQL
- Decided on SQL schema for employee database
- Defining data types for employees table in `assets/data/README.md`
- Linking to SQL and testing table database syncing and migrations

## What did you struggle with?

- What? Why? How?

---

## Licensing Details

- What type of license are you releasing this under?

---

## Further details, related projects, reimplementations

- If it's an API, is there a client app that works with this project? link it

## Employees Database

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
