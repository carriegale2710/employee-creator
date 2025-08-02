package io.carrie.employee.contract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    // spring auto-generates this custom queries for us
    void deleteAllByEmployeeId(Integer employeeId);

    List<Contract> findAllByEmployeeId(Integer employeeId);

}
