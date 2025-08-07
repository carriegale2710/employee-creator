# Frontend - React TypeScript

[![React S3 Deploy](https://github.com/carriegale2710/employee-creator/actions/workflows/react-deploy.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/react-deploy.yml)

This is the frontend for the Employee Creator app, built with React and TypeScript. It provides a user interface for managing employee data, including creating, editing, and deleting employee records.

> For detailed development history and feature updates for this front-end, see [CHANGELOG.md](CHANGELOG.md).

> For full project overview, see [main README](../README.md).

## Table of Contents

- [Quick Setup](#quick-setup)
- [Environment Variables](#environment-variables)
- [Build Commands](#build-commands)
- [Testing](#testing)
- [Tech Details](#tech-details)
- [Architecture Notes](#architecture-notes)
- [Change Log](#change-log)
- [Useful Resources](#useful-resources)
- [Quality Assurance - FRONTEND](#quality-assurance---frontend)

## Quick Setup

```bash
cd front-end
npm install
npm run dev  # http://localhost:5173
```

## Environment Variables

Create `.env` file:

```env
VITE_API_KEY=ExampleApiKey123
```

## Build Commands

```bash
npm run build    # Production build
npm run preview  # Test production build
```

## Testing

```bash
npm run test  # Vitest component tests
```

**Method:** Component Tests - Good for testing individual functionality quickly.
**Main Purpose:** "Does the user interface act like it should when I use it?"

**Goals:** Right now, testing public features only:

- What the user sees (rendering, UI)
- What the user does (onClick, onSubmit, onHover)
- What the user expects (data, nav, buttons)

> Note: Current front-end tests may be outdated/incomplete at the moment due to time constraints of full TDD. Focusing on core functionality with browser testing first.

---

## Tech Details

- **React 18** + **TypeScript** for type safety
- **Vite** for fast development and builds
- **Tailwind CSS** + **SCSS** for component styling
- **React Router** for navigation
- **React Hook Form** for validation
- **Vitest** for component testing

## Architecture Notes

> See `/design-assets` folder for design plans and UI mockups.

- **Component reuse**: Same form component for create/edit
- **Top-down approach**: Backend API defined first, then frontend integration
- **Responsive design**: Mobile-first with Tailwind breakpoints

## Quality Assurance - FRONTEND

### MVP Requirements

- [x] React app compiles and runs (Vite)
- [x] Basic CRUD employee functionality works
- [x] Form validation added
- [x] Optional testing included (Vitest/Zod)
- [x] UI styled + responsive (SCSS/Tailwind)

#### Core Flows

- [x] Page loads with correct data from backend (GET)
- [x] Form inputs accept user input
- [x] Form submits valid data successfully (POST/PATCH)
- [x] Item can be deleted (DELETE)
- [x] Navigation or redirects work after actions

#### Error Handling

- [x] Required fields show validation error if empty
- [x] Invalid input (e.g. bad email) is blocked
- [x] Backend error (e.g. 400 or 500) shows helpful message
- [x] Loading and error states show something visible (message)

#### User Experience

- [x] Mobile/responsive layout works (test one small screen)
- [x] Buttons and links are clickable and have visual feedback
- [x] Basic keyboard navigation works (Tab, Enter)
- [x] No obvious visual glitches after user actions

---

## Useful Resources

- https://react.dev/learn/thinking-in-react
- https://react.dev/learn/reusing-logic-with-custom-hooks
