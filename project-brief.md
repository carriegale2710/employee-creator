# Project Brief

## Purpose

Demonstrates an understanding of:

- writing RESTful APIs with basic CRUD endpoints
- building front-end UIs with React
- integration of backend + frontend code
- deployment of full-stack web applications

## MVP Requirements

<!-- [Project Brief](https://github.com/nology-tech/aus-post-course-guide/tree/main/projects/employee-creator) -->

- We need a web application to manage employees:
  - [x] create
  - [x] list
  - [ ] modify
  - [x] delete
- The schema for the employee is left to the criteria of the candidate.

Also code should be:

- [x] Production ready.
  - [ ] (except for custom domain - healthcheck)
- [ ] Understandable and maintainable by other developers.
- [ ] Robust and handle error situations.
- [x] Bug free, compile and work.
- [x] Include instructions to compile and run the API and the Web app in localhost.
- [x] Hosting (Heroku, AWS, Azure, etc.) is required.
- [x] If your code includes unit tests you may use a unit test framework of your choice.

## Hints

### React hints

- [ ] React Redux middlewear is recommended like thunks or epics to handle async actions.
- [x] Typescript is recommended.
- [ ] React hooks are recommended.
- [ ] You can include any other open source NPM library.
- [x] Feel free to use your favorite CSS framework.
  - [ ] Tailwind is a good choice
- [ ] Add some basic validations on the form like:
  - [ ] input required
  - [ ] input max length
- Reccomended:
  - [ ] React Hook Form
  - [ ] Zod are reccomended
- The site should be responsive.
  - [ ] media queries
  - [x] flexbox

### RESTful API hints

- The list can be a local database
  - [x] MySQL is reccomended
- Implementing an API logging strategy.
  - [x] Log2j **highly** reccomended
- [/] Implementing error handling strategy.
- At least 3 endpoints are required:
  - [x] To create an employee
  - [x] To get a list of existing employees
  - [x] To delete an employee

## Design Snippets (examples from brief)

Backend
![spring-arch](/assets/diagrams/spring-boot-arch-2.png)
![example](/assets/diagrams/layer-example.png)

Frontend
![Employee List Page](/front-end/design-assets/mockups/employee-list.PNG)
![Employee Edit / Create Form 1](/front-end/design-assets/mockups/form-part-1.PNG)
![Employee Edit / Create Form 2](/front-end/design-assets/mockups/form-part-2.PNG)

## Technology / Documentation Recommendations

### Frontend

- [x] Vite with `react-ts`
- [x] Use SCSS instead of CSS, `npm install sass`
  - [ ] Or just use Tailwind
- [ ] [React Form Hook for form validation & submission](https://react-hook-form.com/get-started)
- [ ] [Typescript React Cheatsheet](https://react-typescript-cheatsheet.netlify.app/docs/basic/setup)
- [ ] [React Router for routes](https://reactrouter.com/en/main/start/tutorial)

### Backend

- Dependencies

  - [x] Spring Web,
  - [x] Validation I/O
  - [x] Spring Testing
    - [x] Rest assured
    - [x] mockito
  - [x] Spring Data JPA
  - [x] MySQL Driver
  - [x] Spring Devtools

Testing

- https://spring.io/guides/gs/testing-web/
- https://www.baeldung.com/spring-mock-rest-template
- https://github.com/spring-guides/gs-testing-web

Logging

- https://www.baeldung.com/spring-boot-logging done

CORS Errors

- https://www.baeldung.com/spring-cors done

API Semantics

- https://www.uniprot.org/help/rest-api-headers
- https://restfulapi.net/resource-naming/

---
