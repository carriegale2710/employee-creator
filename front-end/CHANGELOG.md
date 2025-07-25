# Employee Creator Frontend - Change Log

## 25/07/2025 - TypeScript Configuration & Testing Setup

### Frontend

- Fixed TypeScript project references configuration for Vitest testing
- Resolved `toBeInTheDocument` matcher issues with proper jest-dom setup
- Configured proper build pipeline for React TypeScript application
- Updated tsconfig files to properly handle testing environment

---

## 24/07/2025 - Form Implementation & Data Handling

### React UI

- Implemented form React UI components
- Added create functionality: send data via POST requests
- Enhanced form data handling for both Employee and Contract forms
- Improved form submission and data validation

---

## 22/07/2025 - User Experience Design

### User Flow Planning

- Mapped comprehensive user flow diagrams for employee and contract forms
- Designed interaction patterns between components
- Documented form navigation and user journey

---

## 16/07/2025 - Form Features & Testing

### Form Development

- Implemented edit feature with prefilled form data from card selection
- Created basic form inputs and submit button functionality
- Added comprehensive form component tests
- Enhanced user interaction patterns

---

## 15/07/2025 - Component Architecture & Database Updates

### React Components

- Deconstructed React components for List and Form pages
- Improved component separation and reusability

### Database Schema Updates

- Updated frontend to handle new schema where Department is no longer stored directly in Employee
- Adapted to new Contract entity structure with start date and department
- Modified components to handle employees with multiple contracts

---

## 11/07/2025 - AWS Deployment

### Frontend Deployment

- Successfully deployed React frontend as [static app](https://d3bcyx0s1yb5do.cloudfront.net/) in AWS S3 (Simple Storage Service)
- Created React build, test and deploy [Github workflow](.github/workflows/react-deploy.yml)
- Configured CloudFront distribution for global content delivery

---

## 09/07/2025 - Frontend Foundation & Initial Development

### Project Setup

- Deconstructed UI design mockups provided into React components using Figma
- Created bonus data flow Mermaid diagrams for documentation
- Installed Vite + TypeScript + SCSS stack for React frontend development

### Core Components

- Created `employee-service.ts` to handle data fetching from database
- Built basic React presentational components: List, Card, Button
- Implemented employee services: `getAll()`, `getById()`

### Backend Integration

- Implemented WebConfig for CORS error resolution
- Established data flow: Page → List → Card component architecture
- Added basic SCSS styling for Card components
