# Employee Creator Backend - Change Log

## 25/07/2025 - Service Layer Error Handling

- Implemented custom exception handling in service layer
- Created `EmailAlreadyExistsException` for duplicate email validation
- Enhanced `GlobalExceptionHandler` to return proper error messages
- Fixed employee update logic to prevent false positives when updating same employee
- Added `@Transactional` annotation for safe employee deletion with contracts
- Updated service unit tests to include contract repository mocking

**Related frontend changes:** See [TypeScript Configuration & Testing Setup](../front-end/CHANGELOG.md#25072025---typescript-configuration--testing-setup)

---

## 24/07/2025 - Employee Management & Form Validation

### New Features

- **Safe Employee Deletion:** When an employee is removed, all their contracts are automatically deleted too
- **Better Form Updates:** Improved how employee information is updated with better error checking
- **Email Validation Fix:** Fixed bug where the system incorrectly said an email was already taken

### Technical Improvements

- **Data Safety:** Added safeguards to prevent data corruption during employee deletion
- **Better Error Messages:** Users now get clearer feedback when something goes wrong
- **Testing Updates:** Improved automated tests to catch more potential issues

**Impact:** Makes the system more reliable and easier to use when managing employee records.

**Related frontend changes:** See [Form Implementation & Data Handling](../front-end/CHANGELOG.md#24072025---form-implementation--data-handling)

---

## 23/07/2025 - System Architecture & Contract Management

### System Improvements

- **Simplified Data Handling:** Moved complex data processing to the server to make the user interface faster and simpler
- **Better Data Organization:** Improved how employee and contract information is organized and sent to users
- **Cleaner Code Structure:** Reorganized backend code for easier maintenance and future updates

### Contract Management Features

- **Contract System:** Built a complete system for managing employee contracts
- **Database Updates:** Created proper storage for contract information linked to employees
- **Testing Framework:** Added automated testing to ensure contract features work correctly

### Goals & Results

- **Main Goal:** Make the user interface simpler by handling complex tasks on the server
- **Approach:** Move business logic from the user's browser to our servers
- **Benefit:** More consistent performance and easier maintenance

**Related frontend changes:** Frontend simplification reflected in component architecture improvements

---

## 22/07/2025 - Contract Management System

### Contract System Development

**Complete Contract Management:** Built a system to handle employee contracts with proper database relationships

- **Database Design:** Created contract storage that properly links to employee records
- **Employee Relationships:** Set up system where each employee can have multiple contracts over time
- **Department Integration:** Added department lookup system for consistent job categorization
- **Data Validation:** Built in checks to ensure contract information is accurate and complete

### User Interface Features

- **Complete CRUD Operations:** Users can create, view, update, and delete contracts
- **Business Logic:** Added rules and workflows for contract management
- **Database Queries:** Created efficient ways to search and retrieve contract information
- **Data Transfer:** Built secure methods for sending contract data between server and user interface

### Testing & Quality

- **Automated Testing:** Created comprehensive tests to ensure contract features work correctly
- **Data Validation:** Added multiple layers of checking to prevent errors
- **Error Handling:** Built in proper error messages and recovery for when things go wrong

**Business Impact:** Enables flexible contract management supporting multiple contracts per employee over time, essential for HR tracking and compliance.

**Related frontend changes:** See [User Experience Design](../front-end/CHANGELOG.md#22072025---user-experience-design)

---

## 20/07/2025 - Employee Entity Enhancement & Testing

### Database Schema Updates

- **Extended Employee Entity:** Added phone number and address fields for comprehensive employee profiles
- **Enhanced Data Model:** Improved employee information storage capabilities

### Feature Development

**Employee Update Functionality:**

- Implemented `updateById` PATCH endpoint with comprehensive validation
- Applied Test-Driven Development (TDD) methodology with parallel test and implementation development
- Resolved end-to-end test issues for employee update operations

### Testing Improvements

**Test Suite Enhancements:**

- ✅ Fixed individual test execution for `UpdateEmployeeByIdTests`
- ⚠️ Resolved concurrent test execution issues (404 errors during batch runs)
- Added comprehensive validation for duplicate email handling during updates

**Contract Feature Foundation:**

- Leveraged existing employee architecture patterns for rapid contract feature development
- Completed end-to-end and service layer tests for contract functionality

**Related frontend changes:** See [Database Schema Updates](../front-end/CHANGELOG.md#15072025---component-architecture--database-updates)

---

## 19/07/2025 - API Error Handling Implementation

### Error Handling Strategy

**Comprehensive Error Response System:**

- Implemented standardized error handling across all API endpoints
- Added proper HTTP status code mapping for different error scenarios
- Enhanced API reliability and developer experience

### Employee Endpoint Improvements

**Delete Operations:**

- ✅ Return `400 BAD REQUEST` for invalid input parameters
- ✅ Return `204 NO_CONTENT` for successful deletion operations

**Create Operations:**

- ✅ Return `400 BAD REQUEST` for invalid first/last name validation
- ✅ Return `400 BAD REQUEST` for duplicate email constraint violations

**Impact:** Improved API consistency and error debugging capabilities for client applications.

---

## 14/07/2025 - Production Logging Infrastructure

### Logging Strategy Implementation

**Enterprise Logging Setup:**

- **Framework Migration:** Configured SLF4J facade with Log4j2 replacing default Logback
- **Environment Profiles:** Created development profile with console and file logging
- **Log Configuration:** Implemented `log4j2-spring.xml` with structured formatting and filtering
- **Application Integration:** Added logging to `EmployeeApplication` controller layer

### Technical Benefits

- **Debugging:** Enhanced development experience with structured log output
- **Monitoring:** Foundation for production monitoring and troubleshooting
- **Performance:** Efficient log4j2 performance characteristics
- **Flexibility:** Profile-based configuration for different environments

**Production Readiness:** Establishes logging foundation required for enterprise deployment.

---

## 12/07/2025 - Infrastructure & Deployment Preparation

### Health Check Implementation

- **Load Balancer Support:** Created dedicated health check controller endpoint
- **Monitoring Integration:** Enables AWS load balancer health monitoring
- **Deployment Readiness:** Foundation for production infrastructure

### Domain Configuration (In Progress)

- **Custom Domain:** Acquired domain from Porkbun for production deployment
- **CI/CD Pipeline:** Initiated auto-deployment workflow for Spring Boot EC2 deployment
- **Status:** On hold - pending infrastructure configuration completion

**Next Steps:** Complete AWS security group configuration and domain SSL setup.

---

## 10/07/2025 - AWS Production Deployment

### Deployment Success

- **Environment Configuration:** Resolved environment variable loading issues in `application.properties`
- **AWS EC2 Deployment:** Successfully deployed Spring Boot application to production
- **Public Access:** API now available at `ec2-3-107-209-102.ap-southeast-2.compute.amazonaws.com:8080/employees`

### Technical Achievements

- **Infrastructure:** Established production-ready AWS EC2 environment
- **Configuration Management:** Proper environment variable handling for production deployment
- **Service Availability:** 24/7 accessible REST API endpoints

**Milestone:** First successful production deployment with public API access.

---

## 08/07/2025 - Core CRUD API & Testing Infrastructure

### REST API Implementation

**Employee Management Endpoints:**

- `GET /employees` - Retrieve all employee records
- `GET /employees/{id}` - Retrieve specific employee by ID
- `POST /employees` - Create new employee record
- `DELETE /employees/{id}` - Remove employee record

### Testing Infrastructure

**Automated Testing Pipeline:**

- **CI/CD Integration:** Implemented GitHub Actions for automated testing workflows
- **Test Coverage:** Comprehensive end-to-end test cases covering all endpoints and edge cases
- **Database Testing:** H2 in-memory database configuration for isolated test execution
- **Architecture:** ModelMapper integration for simplified entity-DTO transformation

### Technical Foundation

**Development Tools:**

- **Testing Stack:** JUnit, Mockito, REST Assured for comprehensive API testing
- **Data Validation:** JSON schema validation for API request/response consistency
- **Quality Assurance:** Smoke tests and service layer unit tests
- **Documentation:** Endpoint specification and testing protocols

**Impact:** Established robust foundation for scalable employee management system with comprehensive testing coverage.

**Related frontend changes:** See [Backend Integration](../front-end/CHANGELOG.md#09072025---frontend-foundation--initial-development)

---

## 06/07/2025 - 07/07/2025 - API Architecture & Testing Foundation

### System Architecture Design

**API Planning & Documentation:**

- **Endpoint Specification:** Comprehensive mapping of REST endpoints with expected HTTP status codes
- **System Design:** Created Mermaid diagrams for application layers and user flow visualization
- **Technical Research:** Test-Driven Development (TDD) framework evaluation and implementation strategy

### Initial Development

**Core Application Structure:**

- **Spring Boot Setup:** Basic `HomeController` with localhost health check functionality
- **Entity Layer:** `Employee` entity with proper getter/setter implementation
- **Service Architecture:** Foundation controller, service, and repository classes
- **Basic Functionality:** Initial GET endpoint implementation and testing

### Testing Infrastructure Foundation

**End-to-End Testing Setup:**

- **Dependencies:** Spring Boot Test Starter (JUnit, Mockito, testing utilities)
- **Database Strategy:** H2 in-memory database for isolated testing environment
- **API Testing:** REST Assured library integration for automated HTTP request testing
- **Test Data:** H2 database seeding with mock data for comprehensive testing scenarios
- **CI/CD:** GitHub Actions integration with automated test badge reporting

### Documentation & Organization

- **Asset Management:** Organized project documentation and technical specifications
- **Development Workflow:** Established systematic approach to feature development and testing

**Foundation Impact:** Established comprehensive development foundation enabling rapid, test-driven feature development.

---

## 05/07/2025 - Project Initialization & Database Setup

### Project Foundation

**Spring Boot Application Setup:**

- **Framework Installation:** Spring Boot with essential dependencies for web API development
- **Database Integration:** MySQL database configuration and connection setup
- **Environment Configuration:** `application.properties` configuration for development environment

### Database Design

**Schema Planning:**

- **Database Architecture:** Designed employee database schema with MySQL
- **Data Modeling:** Defined comprehensive employee table structure with appropriate data types
- **Documentation:** Detailed data type specifications in `assets/data/README.md`
- **Validation:** Database connection testing and table synchronization verification

### Development Infrastructure

- **Development Environment:** Local MySQL database integration
- **Data Persistence:** JPA/Hibernate configuration for object-relational mapping
- **Architecture Planning:** Established foundation for scalable employee management system

**Project Milestone:** Successfully established development environment with database connectivity and initial schema design.
