package io.carrie.employee.contract;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.carrie.employee.contract.dtos.CreateContractDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);
    private ContractService contractService;

    ContractController(ContractService contractService) {
        this.contractService = contractService;
        logger.info("ContractController initialized");
    }

    @PostMapping
    public ResponseEntity<Contract> create(@Valid @RequestBody CreateContractDTO dto) {
        logger.info("Creating new contract for employee ID: {}", dto.getEmployeeId());

        try {
            Contract newContract = this.contractService.create(dto);
            logger.info("Successfully created contract with ID: {}", newContract.getId());
            return new ResponseEntity<>(newContract, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating contract: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<Contract>> getAll() {
        logger.info("Fetching all contracts");

        try {
            List<Contract> allContracts = this.contractService.findAll();
            logger.info("Successfully retrieved {} contracts", allContracts.size());
            return new ResponseEntity<>(allContracts, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all contracts: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getById(@PathVariable Integer id) {
        logger.info("Fetching contract by ID: {}", id);

        try {
            Contract foundContract = this.contractService.findById(id);
            logger.info("Successfully found contract with ID: {}", foundContract.getId());
            return new ResponseEntity<>(foundContract, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching contract with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Deleting contract with ID: {}", id);

        try {
            this.contractService.deleteContractById(id);
            logger.info("Successfully deleted contract with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting contract with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}
