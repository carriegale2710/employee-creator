package io.carrie.employee.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // spring auto-generates this custom queries for us
    boolean existsByEmail(String email);

    Employee findByEmail(String email);

}
