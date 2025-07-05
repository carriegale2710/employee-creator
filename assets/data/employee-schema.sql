-- drop database employee_creator;
-- create database employee_creator;
use employee_creator;
show tables;
select * from employees;

-- SCHEMA CREATION

CREATE TABLE employees (
  id SERIAL PRIMARY KEY,                     -- Auto-increment ID
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
--   phone VARCHAR(20),
--   date_of_birth DATE,

  job_title VARCHAR(100),
--   department VARCHAR(50),
--   start_date DATE NOT NULL,
--   employment_type VARCHAR(20),              -- e.g. 'Full-time', 'Part-time'

--   salary DECIMAL(10, 2),                    -- e.g. 80000.00

--   tax_file_number VARCHAR(20),             -- Store securely

--   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
