```dbml
- Use DBML to define your database structure
- Docs: https://dbml.dbdiagram.io/docs

Table employees {
  id int [pk, increment] --generated
  given_name varchar [not null] -- first + middle names (up to 5)
  last_name varchar [not null]
  email varchar [unique, not null] -- can be auto-generated based on name
  phone_number varchar [not null] -- should be verified somehow
  address text -- searchable or custom entry
  date_of_birth date --verify not in future, date picker
  tax_file_number varchar [unique] -- security encryption needed
}

-- [Employee → One-to-Many → Contracts]

Table contracts {
  id int [pk, increment] --generated
  employee_id int [not null, ref: > employees.id]
  department varchar // e.g. sales
  contract_type varchar [not null] // e.g., full-time, part-time
  salary_amount decimal
  hours_per_week int -- limit to 40 (exclude overtime)
  start_date date [not null]
  end_date date -- optional if not yet decided
  is_active boolean -- on by default unless past end_date
}

-- [Department → One-to-Many → Contracts]

Table departments {
  id int [pk, increment]
  name varchar [unique, not null]
  num_of_employees int -- count num of employees in this department with active contracts
}

```
