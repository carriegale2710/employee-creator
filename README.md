# Employee Creator Full-Stack Web Application

> A production-ready HR management system built with Java Spring Boot and React TypeScript

**Live Demo**: [Employee Creator App](https://d3bcyx0s1yb5do.cloudfront.net/) | **⚡ API**: [employeecreator.my](https://employeecreator.my)

## [![Spring Boot Tests](https://github.com/carriegale2710/employee-creator/actions/workflows/spring-boot-test.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/spring-boot-test.yml) [![React Deploy](https://github.com/carriegale2710/employee-creator/actions/workflows/react-deploy.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/react-deploy.yml) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Table of Contents

- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [Quick Start](#quick-start)
- [Tech Stack](#techstack)
- [Demo & Screenshots](#demo--screenshots)
- [Core Features](#features)
- [Testing](#testing)
- [Architecture & Design](#architecture--design)
- [Known Issues](#known-issues)
- [Project Retrospective](#project-retrospective)
- [Licensing Details](#licensing-details)

---

## Project Overview

**Employee Creator** is a full-stack CRUD application for managing employee records and contracts. Built over 4 weeks as a solo project, it demonstrates enterprise-level development practices including comprehensive testing, CI/CD deployment, and scalable architecture.

### Key Highlights

- **Full-Stack**: Java Spring Boot API + React TypeScript frontend
- **Comprehensive Testing**: 40+ automated tests (Unit, Integration, E2E)
- **Production Deployed**: AWS infrastructure with automated CI/CD
- **Enterprise Features**: Employee management, contract tracking, form validation
- **Modern Stack**: TypeScript, Tailwind CSS, MySQL, GitHub Actions

### Business Value

**For HR Teams**: Streamline employee onboarding, track contract history, maintain compliance records

**For Developers**: Reference implementation showcasing modern full-stack patterns, testing strategies, and deployment practices

**For Stakeholders**: Production-ready system with scalable architecture and comprehensive audit trails

## Project Structure

> Note: The documentation for this project is split up into backend vs front-end specific locations in the code base. They include more details like build steps, testing and change logs.

See [`employee/README.md`](employee/README.md)
See [`front-end/README.md`](front-end/README.md)

```markdown
📁 employee-creator/
├── 📄 README.md (high-level overview) //you are here
├── 📁 employee/
│   ├── 📄 README.md (backend-specific)
│   └── 📄 CHANGELOG.md (backend changes)
└── 📁 front-end/
    ├── 📄 README.md (frontend-specific)
    └── 📄 CHANGELOG.md (frontend changes)
```

---

## Quick Start

### Prerequisites

- Java 17+
- Node.js 18+
- MySQL 8.0+

### Backend (Spring Boot)

```bash
cd employee
./mvnw clean install
./mvnw spring-boot:run
# API available at: http://localhost:8080
```

### Frontend (React)

```bash
cd front-end
npm install
npm run dev
# App available at: http://localhost:5173
```

### Detailed Setup

For comprehensive setup instructions including environment configuration, database setup, and deployment:

- [Building Backend API](employee/README.md)
- [Building Front-end UI](front-end/README.md)

---

## Techstack

| Layer      | Technology                                             | Purpose                             |
| ---------- | ------------------------------------------------------ | ----------------------------------- |
| DB         | MySQL, JPA                                             | SQL schema control, ORM integration |
| Backend    | Spring Boot, Java, Maven                               | Production-grade APIs, type safety  |
| Frontend   | React, TypeScript, Vite                                | SPA structure, compile-time safety  |
| Testing    | JUnit, Mockito, REST Assured, H2, Faker (Data seeding) | API e2e + unit tests, mock data     |
| Styling    | Tailwind / SCSS                                        | Component-level styling, responsive |
| Deployment | AWS EC2 (Backend API) S3 + CloudFront(Front-end UI)    | Easy CI/CD, low costs               |
| CI/CD      | GitHub Actions                                         | Automated testing and deployment    |
| Monitoring | Log24j                                                 | Logs, error tracking                |

---

## Testing

| Type                  | Tools Used       | Implementation               |
| --------------------- | ---------------- | ---------------------------- |
| Spring Unit Tests     | JUnit + Mockito  | ✅ Service Layers            |
| Spring E2E Tests      | REST Assured, H2 | ✅ 40+ employees / contracts |
| React Component Tests | Vitest           | ⏳ Basic                     |

To run tests, use the following commands:

```bash
./mvnw test      # backend
npm run test     # frontend (if added)

```

---

## Demo & Screenshots

### Hosted Links

- **Frontend:** [Live Demo](https://d3bcyx0s1yb5do.cloudfront.net/)
- **Backend API:** [API Documentation](https://employeecreator.my)

### Application Screenshots

| View                 | Screenshot                                                                        |
| -------------------- | --------------------------------------------------------------------------------- |
| **Home Page**        | ![Home](front-end/design-assets/mockups/version2.0/image-1.png)                   |
| **Employee List**    | ![Employee List Page](front-end/design-assets/mockups/version2.0/image.png)       |
| **Add Employee**     | ![Register Employee Form](front-end/design-assets/mockups/version2.0/image-2.png) |
| **Employee Profile** | ![Employee Profile](front-end/design-assets/mockups/version2.0/image-3.png)       |

---

## Features

### :man_office_worker: Employee Management

- **View All Employees** - Paginated list with search and filter capabilities
- **Employee Profiles** - Detailed view showing personal info, contracts, and actions
- **Add Employee** - Registration form with validation and confirmation
- **Edit Employee** - Update existing employee information
- **Delete Employee** - Remove employee with confirmation dialog

### 📄 Contract Management

- **View All Contracts** - Paginated list with employee filtering
- **Create Contract** - Link new contracts to existing employees
- **Employee Contracts** - View all contracts for a specific employee
- **Delete Contract** - Remove contracts with proper authorization (WIP)

> **Note:** Each employee can have multiple contracts. Contracts are treated as immutable legal documents with no update functionality by design.

### 🚀 Future Goals

- Advanced search and filtering for employee list
- Contract draft management system
- Department management module
- Enhanced form confirmation steps

---

## Architecture & Design

### MVP Requirements

- Build a full-stack app:
  - Backend: Spring Boot REST API (CRUD for employees)
  - Frontend: React + TypeScript
- Must be production-ready, testable, and deployed
- Include:

  - Basic validations
  - Responsive layout
  - Hosting (AWS)

### Design Goals

1. **User-Centric**: Prioritize user experience and usability in all design decisions.
2. **Scalability**: Ensure the application can scale with increasing data and user load.
3. **Maintainability**: Write clean, modular code that is easy to understand and maintain.
4. **Testability**: Implement thorough testing at all levels (unit, integration, E2E) to ensure reliability.

### Approach

1. **Agile Methodology**: Follow an iterative development process with regular feedback loops from stakeholders.
2. **TDD**: Use Test-Driven Development to define and validate functionality before implementation.
3. **Component-Based**: Use a component-based approach for the frontend to promote reusability and separation of concerns.

## Design Implementation

### Backend decisions

- Used top-down TDD to define backend features and to run within CI/CD pipeline.
- Included a contracts with a `one-to-many relationship` for `employees -> contracts`
- This allows for flexible, quicker UX when updating of DB records via in FE client app with only minor updates eg. salary, contract dates etc.
- Separated concerns by having a dedicated contracts table, allowing for better organization and management + security of employee contracts.

### Front-end decisions

- Used React Router for seamless navigation between views.
- Implemented form validation using React Hook Form for schema validation.
- Used Vitest for minimal component testing to ensure UI functionality.
- Implemented a responsive design using Tailwind CSS and SCSS for component-level styling.

## Known issues

<!-- Remaining bugs, things that have been left unfixed:

Features that are buggy / flimsy/not functional yet: -->

- [5/08/25] Contract deletion is not fully implemented yet in front-end (WIP)

- [4/08/25] Pagination needed for employee list to reduce API load times and improve UX

- [4/08/25] Contract form should not have employee ID field - this is a UX issue (risk of human error)

- [25/07/25] React unit tests are still WIP

---

## QA Checklist

### 🔧 Backend (Spring Boot)

- [x] App compiles and runs
- [x] API has working CRUD endpoints (GET, CREATE, DELETE + EDIT)
- [x] Unit + end-to-end tests (JUnit, Mockito)
- [x] Error handling implemented
- [x] Logging strategy in place

### 💻 Frontend (React + TypeScript)

- [x] React app compiles and runs (Vite)
- [x] Basic CRUD employee functionality works
- [x] Form validation added
- [x] Minimal Component testing included (Vitest)
- [x] UI styled + responsive (SCSS/Tailwind)

#### 🚚 Delivery & Deployment (Both)

- [x] README includes clear setup steps for both API and Web app (local dev)
- [x] Hosting link works (Heroku, AWS, Azure, etc.)
- [x] App is production-ready
- [x] Code is clean + well documented
- [x] Codebase is understandable and maintainable
- [x] Bug-free and everything compiles + runs as expected

---

## Project Retrospective

This project was a solo endeavor to build a full-stack web application that simulates real-world business scenarios. It aimed to apply best practices in software development, including:

- **Microservices architecture** thinking
- **Separation of concerns** principle
- **Production deployment** patterns
- **Documentation** and changelog management

### Development Experience & Outcomes

| **Area**                   | **Skills Gained**                                                                     | **Key Challenge**                       | **Solution & Learning**                                                                        |
| -------------------------- | ------------------------------------------------------------------------------------- | --------------------------------------- | ---------------------------------------------------------------------------------------------- |
| **Full-Stack Development** | Frontend/backend integration, API design, CORS handling, state management             | AWS backend/frontend integration issues | Resolved with proper CORS configuration and security group adjustments                         |
| **Testing Strategy**       | TDD methodology, unit/integration testing, balancing coverage with delivery speed     | Balancing TDD with delivery speed       | Hybrid approach: browser testing first, then writing tests worked better for solo projects     |
| **DevOps & Deployment**    | AWS deployment (EC2, S3, CloudFront), GitHub Actions CI/CD, production configurations | GitHub Actions configuration complexity | Multiple iterations needed - learned to test pipeline changes incrementally                    |
| **Code Quality**           | Git workflows, branching strategies, code review processes, clean commit histories    | Simulating team development practices   | Used `main` + `dev` branches with feature branching and advanced Git commands (rebase, squash) |
| **Architecture**           | Microservices thinking, separation of concerns, scalable application structure        | Managing monorepo documentation         | Split by domain (frontend/backend) with regular changelog updates for better maintainability   |

---

## Licensing Details

<!-- What type of license are you releasing this under? -->

MIT License.

---
