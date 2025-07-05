# Employee Creator

## Introduction

Your first step in the recruitment process is to demonstrate your understanding of writing RESTful APIs in Java and frontend using React Typescript.

When writing your code, please be mindful of the following:

-   Your code should be production ready.
-   Your code should be understandable and maintainable by other developers.
-   Your code should be robust and handle error situations.
-   Your code should be bug free, compile and work. Please include instructions to compile and run the
    API and the Web app in localhost. Hosting (Heroku, AWS, Azure, etc.) is required.
-   If your code includes unit tests you may use a unit test framework of your choice.

## The requirement

We need a web application to create, list, modify and delete employees. The application should consist of a spring
RESTful API and a React Typescript frontend. The schema for
the employee is left to the criteria of the candidate.

## React hints:

-   Typescript is recommended.
-   React hooks are recommended.
-   You can include any other open source NPM library.
-   Feel free to use your favorite CSS framework.
-   Feel free to use your favorite middleware.
-   Add some basic validations on the form like required and max length validations.
-   The site should be responsive.

## RESTful API hints:

-   The list can be a local database, CSV, TXT file or even in memory
-   Implementing an API logging strategy.
-   Implementing error handling strategy.
-   At least 3 endpoints are required:
    -   To create an employee
    -   To get a list of existing employees
    -   To delete an employee

## Design Snippets

![Employee List Page](assets/employee-list.PNG)
![Employee Edit / Create Form 1](assets/form-part-1.PNG)
![Employee Edit / Create Form 2](assets/form-part-2.PNG)

## Technology / Documentation Recommendations

### Frontend

-   Vite with `react-ts`
-   Use SCSS instead of CSS, `npm install sass`
-   React Form Hook for form validation & submission
    -   https://react-hook-form.com/get-started
-   Use HTML attributes to required / validate fields before writing your own validation
-   Typescript React Cheatsheet
    -   https://react-typescript-cheatsheet.netlify.app/docs/basic/getting-started/basic_type_exampleReact Cheat Sheet
-   Use Axios over fetch in the frontend for testing mocks
-   React Router for routes
    -   https://reactrouter.com/en/main/start/tutorial
-   Mock Axios Calls
    -   https://stackoverflow.com/questions/70450576/how-to-test-react-component-with-axios-request-in-useeffect

### Backend

-   Dependencies
    -   Spring Web,
    -   Validation I/O
    -   Spring Testing
    -   Spring Data JPA
    -   MySQL Driver
    -   Spring Devtools
-   application.properties starter:

```
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.username=root
spring.datasource.password=MyPass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.generate-ddl=true
```

-   Testing
    -   https://spring.io/guides/gs/testing-web/
    -   https://www.baeldung.com/spring-mock-rest-template
-   Logging
    -   https://www.baeldung.com/spring-boot-logging
-   CORS Errors
    -   https://www.baeldung.com/spring-cors
-   API Semantics
    -   https://www.uniprot.org/help/rest-api-headers
    -   https://restfulapi.net/resource-naming/
