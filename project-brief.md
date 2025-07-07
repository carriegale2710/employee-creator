## Project Brief

### Purpose

Demonstrates an understanding of:

- writing RESTful APIs with basic CRUD endpoints
- building front-end UIs with React
- integration of backend + frontend code
- deployment of full-stack web applications

### MVP Requirements

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

### Hints

#### React hints

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

#### RESTful API hints

- The list can be a local database
  - [x] MySQL is reccomended
- Implementing an API logging strategy.
  - [ ] Log2j **highly** reccomended
- [ ] Implementing error handling strategy.
- At least 3 endpoints are required:
  - [ ] To create an employee `/create`
  - [ ] To get a list of existing employees `/list`
  - [ ] To delete an employee `delete?id={id}`

### Design Snippets (examples from brief)

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
