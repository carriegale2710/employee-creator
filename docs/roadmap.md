# Roadmap

Agreed 04/11/2026. Goal: make the project stand out to engineers doing technical screens, through depth rather than extra screens. Two depth tracks: auth and roles, and scale and performance.

## Phase 1: Foundations

- [#55](https://github.com/carriegale2710/employee-creator/issues/55): fix employee status.
  - `NEW_HIRE` means the earliest contract started within the last 90 days and is still active. The 90 days is a named constant.
  - `isActive()` respects `startDate`, so a contract starting in the future is not active.
- [#36](https://github.com/carriegale2710/employee-creator/issues/36): fix CORS allowed origins.
- [#37](https://github.com/carriegale2710/employee-creator/issues/37): remove hardwired `spring.profiles.active=dev`.
- [#33](https://github.com/carriegale2710/employee-creator/issues/33): fix DataSeeder contract assignment.

## Phase 2: Performance

- [#56](https://github.com/carriegale2710/employee-creator/issues/56): paginate the employee list.
- Compute status in SQL in the list query and return a list DTO, so contracts are not loaded per employee.
- Add search and filtering to the paginated list, with database indexes.
- Seed about 10,000 employees locally and publish before/after SQL query counts and response times for `GET /employees`.
- Add a test that locks in the query count.

The 10,000-employee data is for local measurement only, not the free live database.

## Phase 3: Auth and roles

- Spring Security with server-side sessions in `HttpOnly` cookies and CSRF protection.
- Admin has full access.
- Viewer is read-only, with salary removed from API responses.
- One-click "Try as admin" and "Try as viewer" demo logins, using seeded accounts that exist only in the demo environment.
- A scheduled backend job resets the fake data, enabled only by a demo-specific profile.

Sessions stay in server memory, so a restart or Render's free service sleeping logs everyone out. This is acceptable for a demo. Spring Session can be added later if needed.

## Phase 4: UI

- [#50](https://github.com/carriegale2710/employee-creator/issues/50): list delete button, as an admin-only action.
- [#52](https://github.com/carriegale2710/employee-creator/issues/52): role title on the employee card.
- [#51](https://github.com/carriegale2710/employee-creator/issues/51): time-boxed pass for consistent states and spacing.

## Deferred

- [#53](https://github.com/carriegale2710/employee-creator/issues/53): profile images for seeded employees.
- [#54](https://github.com/carriegale2710/employee-creator/issues/54): validated address field.
- k6 load testing.
- A third employee role with access to only their own record.

## Decisions and alternatives

| Decision | Chosen | Why | Rejected |
| ---------------- | ---------------------------- | --------------------------------------------------------------------- | ------------------------------------------------- |
| Login | Server-side session cookies | JavaScript can't read an `HttpOnly` cookie | Self-managed JWTs, external provider |
| Demo access | One-click demo logins | Almost no friction, and shows role differences at once | Published credentials, anonymous read-only access |
| Salary for viewers | Removed in the API response | Enforced on the server, not just hidden in the UI | Full read access, three roles |
| Performance proof | Query counts and timings | Targets the actual problem and is repeatable | k6 load test |
| List status | Computed in SQL | Never stale, and can be filtered and sorted in the database | Batch loading, stored column, dropping status |
| Build order | Performance before auth | The salary-free viewer response is built on the final list DTO | Auth first |
