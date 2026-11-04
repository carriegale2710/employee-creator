# Issues

Over-engineering audit, 04/11/2026. Findings are ranked by biggest cut first within each section. Nothing here has been applied. Line counts are approximate.

Tags:

- `delete`: dead code, unused flexibility, or a speculative feature.
- `stdlib`: hand-rolled code that a standard or already-installed tool covers.
- `native`: a dependency or code doing what the platform already does.
- `yagni`: an abstraction, config, or layer that isn't needed.
- `shrink`: the same logic in fewer lines.

## Backend

1. `delete:` "Not in Use" EC2 workflow, which still runs on pushes to `main`. Replacement: nothing, Render deploys. [spring-ec2-deploy.yml](../.github/workflows/spring-ec2-deploy.yml)
2. `delete:` 5 JSON files no test references. Replacement: nothing. Keep `contract-schema` and `contract-list-schema`, which `employee-schema` reaches via `$ref`. [create-contract-dto-schema.json](../employee/src/test/resources/schemas/contracts/dtos/create-contract-dto-schema.json), [employees/dtos/](../employee/src/test/resources/schemas/employees/dtos/)
3. `native:` Log4j2 starter, XML config, component properties and 4 exclusion blocks. Logback also stays on the classpath through an unexcluded path, so two SLF4J bindings ship today. Replacement: Boot default logging plus `logging.level.io.carrie=debug`; add `logging.file.name` if file logs matter. [pom.xml](../employee/pom.xml), [log4j2-spring.xml](../employee/src/main/resources/log4j2-spring.xml), [log4j2.component.properties](../employee/src/main/resources/log4j2.component.properties)
4. `delete:` "Hello, World" controller and the test that exists only for it. Replacement: nothing; `EmployeeApplicationTests` still covers context load. [HomeController.java](../employee/src/main/java/io/carrie/employee/HomeController.java), [SmokeTest.java](../employee/src/test/java/io/carrie/employee/SmokeTest.java)
5. `yagni:` nothing references the actuator dep, and the `/healthcheck` try/catch can't fail. Replacement: drop actuator and write `@GetMapping("/healthcheck") String health() { return "OK"; }`, so UptimeRobot keeps working. [HealthCheckController.java](../employee/src/main/java/io/carrie/employee/HealthCheckController.java)
6. `yagni:` `pom.xml` empty scaffolding (`url`, `licenses`, `developers`, `scm`) and direct `mockito-core` and `json-path` deps. Replacement: nothing; `starter-test` and `rest-assured` already bring both libraries. [pom.xml](../employee/pom.xml)
7. `delete:` `SearchBar.md`; no search bar exists. Replacement: nothing. The rest of `employee/assets` is also unlinked from the README and docs, so link it or drop it. [SearchBar.md](../employee/assets/diagrams/flows/SearchBar.md)
8. `delete:` `CommandLineRunner`, console message and commented log tests. Replacement: bare `main`. [EmployeeApplication.java](../employee/src/main/java/io/carrie/employee/EmployeeApplication.java)
9. `stdlib:` ModelMapper with 2 beans for two 5-field mappings. Replacement: `Employee.builder()` for create and explicit non-blank setters for update, keeping the skip-blank behaviour. Update the `@Mock` in `EmployeeServiceTest`. [ModelMapperConfig.java](../employee/src/main/java/io/carrie/employee/config/ModelMapperConfig.java), [EmployeeService.java](../employee/src/main/java/io/carrie/employee/employee/EmployeeService.java)
10. `shrink:` date fields typed `String`, plus `@DateTimeFormat`, the `setEndDate` blank check, date trimming and `LocalDate.parse`. Replacement: `LocalDate` fields; lenient Jackson maps `""` to null. Update the tidy and validate tests. [CreateContractDTO.java](../employee/src/main/java/io/carrie/employee/contract/dtos/CreateContractDTO.java), [ContractService.java](../employee/src/main/java/io/carrie/employee/contract/ContractService.java)
11. `shrink:` manual `isEmpty`/`get` lookups, repo passed to its own service, `existsBy` then `findBy`, and `deleteById` always returning `true`. Replacement: `orElseThrow`, `findByEmail` alone and `void`. [EmployeeService.java](../employee/src/main/java/io/carrie/employee/employee/EmployeeService.java), [ContractService.java](../employee/src/main/java/io/carrie/employee/contract/ContractService.java)
12. `delete:` `getActiveContracts`, and `TERMINATED` and `ON_LEAVE`, which are never returned. Replacement: nothing; remove them from the Java enum and schema. [Employee.java](../employee/src/main/java/io/carrie/employee/employee/Employee.java), [EmployeeStatus.java](../employee/src/main/java/io/carrie/employee/employee/enums/EmployeeStatus.java), [employee-schema.json](../employee/src/test/resources/schemas/employees/employee-schema.json)
13. `delete:` manual contract delete, duplicated by `CascadeType.ALL` and `@OnDelete`. Replacement: `employeeRepository.deleteById`; update the `verify` in `EmployeeServiceTest`. [EmployeeService.java](../employee/src/main/java/io/carrie/employee/employee/EmployeeService.java)
14. `delete:` `@ResponseStatus` on both exceptions; `GlobalExceptionHandler` already sets the status. Replacement: nothing. [common/exceptions/](../employee/src/main/java/io/carrie/employee/common/exceptions/)
15. `delete:` `generate-ddl`, `database-platform` and default `server.address`. Replacement: `ddl-auto`, dialect auto-detection and Tomcat's default bind address. [application.properties](../employee/src/main/resources/application.properties)

Backend net: -555 lines (approx.), -5 deps possible.

## Frontend

1. `native:` `normalize.scss` and its import. Replacement: Tailwind Preflight, already loaded by `@tailwind base` and built on modern-normalize. [normalize.scss](../front-end/src/normalize.scss)
2. `delete:` "Not in Use" S3/CloudFront workflow, which still runs on pushes to `main`. Replacement: nothing, Render deploys. [react-deploy.yml](../.github/workflows/react-deploy.yml)
3. `native:` `sass` dep. The stylesheets use no Sass features beyond `//` comments. Replacement: rename to `.css`, and delete the specificity lecture comments and commented-out `nav` block. [App.scss](../front-end/src/App.scss), [index.scss](../front-end/src/index.scss)
4. `delete:` tracked `tsconfig.tsbuildinfo` and compiled `test-setup.js`; Vite uses `test-setup.ts`. Replacement: nothing. [tsconfig.tsbuildinfo](../front-end/tsconfig.tsbuildinfo), [test-setup.js](../front-end/config/test-setup.js)
5. `shrink:` form logging, `console.*` noise, `if (!x)` then `if (x)`, unused return values, no-op `finally` and Cancel `onClick` logs. Replacement: one `if`/`else` with only the `catch` handling. [EmployeeForm.tsx](../front-end/src/containers/EmployeeForm/EmployeeForm.tsx), [ContractForm.tsx](../front-end/src/containers/ContractForm/ContractForm.tsx)
6. `delete:` unused `getContracts` and `getContractById`, plus `headers` that `apiCall` already sets. Replacement: nothing. [contracts.ts](../front-end/src/services/contracts.ts), [employees.ts](../front-end/src/services/employees.ts)
7. `shrink:` `Button` custom prop types, `altText`, unused `link` variant and wrapper fragment. Replacement: `ComponentProps<"button">` and native `aria-label`. [Button.tsx](../front-end/src/components/Button/Button.tsx)
8. `delete:` unused `contract2` and `mockContracts`. Replacement: nothing. [mockContracts.tsx](../front-end/src/services/mockContracts.tsx)
9. `shrink:` `forwardRef`, and `Select` `defaultValue` logic no caller uses. Replacement: `ref` as a prop on React 19, and `defaultValue=""`. [Input.tsx](../front-end/src/components/Input/Input.tsx), [Select.tsx](../front-end/src/components/Select/Select.tsx)
10. `delete:` `App.test.tsx`, which only asserts `true`. Replacement: nothing. [App.test.tsx](../front-end/src/App.test.tsx)
11. `shrink:` `useValidation` hook that uses no hooks, unused `useEmployeeList` return values and the `ContractCard` null branch. Replacement: exported constants, `{ employees, loading }` and a non-null prop. [useValidation.ts](../front-end/src/hooks/useValidation.ts), [useEmployeeList.tsx](../front-end/src/hooks/useEmployeeList.tsx), [ContractCard.tsx](../front-end/src/components/ContractCard/ContractCard.tsx)
12. `shrink:` no-op `toUpperCase().split("_").join(" ")` on constants that are already formatted. Replacement: `options={DEPARTMENTS}` and `options={CONTRACT_TYPES}`. [ContractForm.tsx](../front-end/src/containers/ContractForm/ContractForm.tsx)
13. `delete:` `TERMINATED` and `ON_LEAVE` from the TS type, since the backend never returns them. Replacement: nothing. [employees.ts](../front-end/src/services/employees.ts)
14. `delete:` `@tailwindcss/vite`; a test build without it produced byte-identical CSS, because PostCSS Tailwind v3 does the work. Replacement: nothing. [vite.config.ts](../front-end/vite.config.ts), [package.json](../front-end/package.json)
15. `delete:` unlinked `design-assets`, about 1.5 MB and not counted in the line total. Replacement: link it from the README or drop it. [design-assets/](../front-end/design-assets/)

Frontend net: -450 lines (approx.), -2 deps possible.
