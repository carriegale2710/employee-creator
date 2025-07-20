package io.carrie.employee.contract;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    // spring auto-generates this custom queries for us

}
