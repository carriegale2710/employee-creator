# Client App (Employee-Creator Front-end)

[![React S3 Deploy](https://github.com/carriegale2710/employee-creator/actions/workflows/react-deploy.yml/badge.svg)](https://github.com/carriegale2710/employee-creator/actions/workflows/react-deploy.yml)

## Techstack

- React: SPA for responsive layout
- Typescript: validating forms for user input before being sent to DB saves time in both DX and UX.
- SCSS/Tailwind: For modular, reusable styles and themes based on design systems.

## Mockups (brief)

![Employee List Page](/front-end/design-assets/mockups/employee-list.PNG)
![Employee Edit / Create Form 1](/front-end/design-assets/mockups/form-part-1.PNG)
![Employee Edit / Create Form 2](/front-end/design-assets/mockups/form-part-2.PNG)

## Wireframes

List Page
![List Page Wireframe](/front-end/design-assets/wireframes/ListPage-wireframe.png)

Form Page
![Form Page Wireframe](design-assets/wireframes/FormPage-Wireframe.png)

## Flows

CRUD Flow between React components
![CRUD data flow diagram](design-assets/flows/crud-flow.png)

## React Components

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

## React + TypeScript + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

### Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type-aware lint rules:

```js
export default tseslint.config([
  globalIgnores(["dist"]),
  {
    files: ["**/*.{ts,tsx}"],
    extends: [
      // Other configs...

      // Remove tseslint.configs.recommended and replace with this
      ...tseslint.configs.recommendedTypeChecked,
      // Alternatively, use this for stricter rules
      ...tseslint.configs.strictTypeChecked,
      // Optionally, add this for stylistic rules
      ...tseslint.configs.stylisticTypeChecked,

      // Other configs...
    ],
    languageOptions: {
      parserOptions: {
        project: ["./tsconfig.node.json", "./tsconfig.app.json"],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
]);
```

You can also install [eslint-plugin-react-x](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-x) and [eslint-plugin-react-dom](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-dom) for React-specific lint rules:

```js
// eslint.config.js
import reactX from "eslint-plugin-react-x";
import reactDom from "eslint-plugin-react-dom";

export default tseslint.config([
  globalIgnores(["dist"]),
  {
    files: ["**/*.{ts,tsx}"],
    extends: [
      // Other configs...
      // Enable lint rules for React
      reactX.configs["recommended-typescript"],
      // Enable lint rules for React DOM
      reactDom.configs.recommended,
    ],
    languageOptions: {
      parserOptions: {
        project: ["./tsconfig.node.json", "./tsconfig.app.json"],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
]);
```
