# Employee Creator Frontend / Client App

[![React S3 Deploy](https://github.com/carriegale2710/employee-creator/actions/workflows/react-deploy.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/react-deploy.yml)

---

## Introduction

### Implementation: Front-end decisions

<!-- Why did you implement this the way you did? -->

- Used top-down TDD to define backend before connecting to frontend.
- Write up basic tests before coding to understand functionality, entity shapes & edge cases.

### Techstack

- React: SPA for responsive layout
- Typescript: validating forms for user input before being sent to DB saves time in both DX and UX.
- SCSS/Tailwind: For modular, reusable styles and themes based on design systems.

---

## Setup

### React Frontend Setup Steps

TBA

1. Installation
2. Setup
3. Running the React App

---

## Testing

The app UI is pretty minimal and straightforward (just CRUD), so I will only test internals/implementation for now in order to speed up production.

More advanced testing like data and fetching should be abstracted to the backend, hence e2e and integration tests will be pushed back until more advanced features like signup/login flows are created.

FE testing will be more for ensuring correct validation/error handling on create/edit features in the Form component, as this is where most unexpected user inputs/interaction may occur.

### Running tests

Techstack used: Vitest + Zod

```bash
npm run test
```

### Test Approach

- Main Purpose: "Does the user interface act like it should when I use it?"

- TDD: write test, pass, refactor for each new feature/component. Avoid overbuilding before MVP.

- Component Tests: Good for testing individual functionality quickly.

Right now, testing public features only:

- What the user sees (rendering, UI)
- What the user does (onClick, onSubmit, onHover)
- What the user expects (data, nav, buttons)

| Test Type      | Example Test                               |
| -------------- | ------------------------------------------ |
| UI rendering   | “Form loads with all fields”               |
| User flow      | “User can submit form and see success msg” |
| Validation     | “Shows error for empty email”              |
| Error handling | “Shows message if server fails”            |

---

### Design Goals / QA Checklist

#### 💻 Frontend MVP (React + TypeScript)

- [x] React app compiles and runs (Vite)
- [ ] Basic CRUD employee functionality works
- [ ] Form validation added
- [x] Optional testing included (Vitest/Zod)
- [ ] UI styled + responsive (SCSS/Tailwind)

#### Core Flows

- [x] Page loads with correct data from backend (GET)
- [ ] Form inputs accept user input
- [ ] Form submits valid data successfully (POST/PUT)
- [ ] Item can be deleted (DELETE)
- [ ] Navigation or redirects work after actions

#### Error Handling

- [ ] Required fields show validation error if empty
- [ ] Invalid input (e.g. bad email) is blocked
- [ ] Backend error (e.g. 400 or 500) shows helpful message
- [ ] Loading and error states show something visible (e.g. spinner or alert)

#### User Experience

- [ ] Mobile/responsive layout works (test one small screen)
- [ ] Buttons and links are clickable and have visual feedback
- [ ] Basic keyboard navigation works (Tab, Enter)
- [ ] No obvious visual glitches after user actions
- [ ] Toasts or alerts auto-dismiss after success

---

## Features

Note: \* = MVP (priority)

### User Stories

| ID  | Feature               | User Wants To...         | So They Can...            | User should be able to...                                                        |
| --- | --------------------- | ------------------------ | ------------------------- | -------------------------------------------------------------------------------- |
| 1   | `List Employees`      | \*See all employees      | Review records            | Click link to view a paginated list of all employee records                      |
| 2   | `Create Employee`     | \*Add a new employee     | Register new hire         | Click button that opens a form to add a new employee as a new record in DB       |
| 3   | `Delete Employee`     | \*Delete employee        | Remove old record         | Click a button to delete a record of an existing employee in DB                  |
| 4   | `Update Employee`     | \*Edit existing employee | Fix errors or update info | Click an edit button that opens a form pre-filled with data to update the record |
| 5   | `Find Employee by ID` | Search specific employee | View or confirm details   | Enter an ID in a search box and fetch/display the record if it exists            |

### 📄 Contract Features Table

Each **employee can have multiple contracts**, and contracts are managed separately but linked to employees (like foreign key via `employeeId`).

| ID  | Feature                         | User Wants To...                    | So They Can...                 | User should be able to...                                                            |
| --- | ------------------------------- | ----------------------------------- | ------------------------------ | ------------------------------------------------------------------------------------ |
| 1   | `List Contracts`                | See all contracts                   | Review records                 | Click link to view a paginated list of all contracts (with optional employee filter) |
| 2   | `Create Contract`               | \*Add a new contract                | Register new agreement         | Click button that opens a form to add a new contract linked to an employee           |
| 3   | `Delete Contract`               | Remove a contract                   | Get rid of outdated info       | Click a delete button to remove a contract from the DB                               |
| 4   | `Update Contract`               | Edit an existing contract           | Fix terms or extend a contract | Click an edit button to open a form with existing values and update the record       |
| 5   | `Find Contract by ID`           | \*View a specific contract          | Check specific terms/details   | Enter a contract ID to fetch and display its details                                 |
| 6   | `List Contracts by Employee ID` | \*View all contracts for 1 employee | Track one employee’s history   | Click from employee page to view all contracts linked to that employee               |

---

## Design Snippets

### Mockups (brief)

![Employee List Page](/front-end/design-assets/mockups/employee-list.PNG)
![Employee Edit / Create Form 1](/front-end/design-assets/mockups/form-part-1.PNG)
![Employee Edit / Create Form 2](/front-end/design-assets/mockups/form-part-2.PNG)

### Wireframes

#### List Page

![List Page Wireframe](/front-end/design-assets/wireframes/ListPage-wireframe.png)

#### Form Page

![Form Page Wireframe](design-assets/wireframes/FormPage-Wireframe.png)

#### Component Nesting

```mermaid
---
config:
  layout: elk
  theme: neo-dark
---
flowchart TD
 subgraph Card["Card"]
        L1["UserData"]
        L2["Button: Edit"]
        L3["Button: Delete"]
  end
 subgraph List["List"]
        Card
  end
 subgraph Form["Form"]
        B1["Form field <br>(onEdit: prefilled)"]
        B2["Button: Submit"]
  end
 subgraph ListPage["ListPage"]
        List
        A3["Button : Add"]
  end
 subgraph FormPage["FormPage"]
        Form
        B4["Button: Cancel"]
  end
    A3 --> FormPage
    B4 --> ListPage
    L2 --> FormPage

```

### Flows

#### Data Flow between React components

![CRUD data flow diagram](design-assets/flows/crud-flow.png)

### User Flows

#### Employee Add/Edit Form

```mermaid
flowchart TD

%% Entry
A0[User views Employee List]
  --> A1[Clicks 'Create' or 'Edit']
  --> A2[Navigates to Employee Form Page]
  --> A3[Clicks 'Exit'] --> Z2[Back to Employee List]

%% Form Mode Check
A2 --> A4{New Employee?}

%% Create Flow
A4 -- Yes --> B1[Render empty form]
  --> B2[User enters details]
  --> B3[Clicks Submit]
  --> B4[POST /employees] --> D2[Loading...]

%% Edit Flow
A4 -- No --> C1[Render form with pre-filled data]
  --> C2[User updates details]
  --> C3[Clicks Submit]
  --> C4[PATCH /employees/:id] --> D2

%% Result
D2 --> D3{Success?}
D3 -- No --> E1[Show validation errors]
D3 -- Yes --> E2[Show success message]

%% Contract Prompt
E2 --> G1[Prompt: 'Add/Edit Contract?']
G1 -->|Yes| Z1[Go to Contract Form]
G1 -->|No| Z2

```

#### Contract Add/Edit Form

```mermaid
flowchart TD

%% Entry Points

A1[Entry Point 1: User finishes creating new employee]
--> A2[Prompt: Add Contract Now?]
A2 -->|No| Z1[Redirect to Employee List]
A2 -->|Yes| B2

Z1 --> A3[Entry Point 2: User views Employee List]
--> A4[Clicks 'Add Contract' on employee card]
--> B2

%% Contract Creation Flow
B2[Show empty contract form]
--> B4[User fills out contract form]
--> B5[User clicks submit button]
--> B6[POST /contracts]
--> B7[Loading...]
--> B8{Success?}
B8 -- Yes --> C1[Show success message] --> Z1
B8 -- No --> C2[Show validation errors] -->B4

```

---

<!--
### React Code Structure

| File                   | Purpose                                                  |
| ---------------------- | -------------------------------------------------------- |
| `employeeService.js`   | Handles raw API calls (GET, POST, PUT, DELETE)           |
| `useEmployees.js`      | Manages local state + calls service + keeps list in sync |
| `EmployeeListPage.jsx` | Renders the list and uses the hook                       |
| `EmployeeForm.jsx`     | Reuses hook to trigger updates after submit              |
 -->

---

## Change logs

### 09/07/2025 - Front-end Planning + Creation

- deconstruct UI design mockups provided into React components using Figma (+ bonus data flow Mermaid diagram)
- install Vite + TypeScript + Scss to create React front-end
- create employee-service.ts to render fetch data from DB
- create basic React presentational components: List, Card, Button
- employee services: getAll() , getById()
- backend: Implemented Webconfig for CORS errors
- render and pass data from Page -> List -> Card
- basic scss styling of Card

### 11/07/2025 - S3 React Deployment

- Deployed React front-end as [static app](https://d3bcyx0s1yb5do.cloudfront.net/) in AWS S3 (Simple Storage Service):
- Created React build, test and deploy [Github workflow](.github/workflows/react-deploy.yml)

### 15/07/2025

- Deconstructed React components for List and Form pages.

Database Schema Updates:

- Department is no longer stored directly in the Employee schema
- Start date and department are now part of the Contract entity
- Employees can have multiple contracts
- See updated ['Database Structure' README](assets/data/README.md) for more

### 16/07/2025

- Edit feature prefilled form with data from card + added changes
- Create basic form inputs and submit button
- Form component tests

### 22/07/2025

- Mapped user flow diagrams for employee and contract forms

---

## Agile Board

### In progress

### Sprint

Form React UI for create/edit features:

- create : send data POST
- save formData from both Employee and Contract forms
- only fetch until both are collected

User flow

- validation with zod

### Backlog

Form validation

UI styling - Global styling (tailwind)

- install tailwind
- use tailwind play to preview designs before implementation
- gather and import any design system assets
- structure in index and partials/variables: color palette, typography
- delete any redundant scss files

---
