package io.carrie.employee.contract;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.carrie.employee.contract.dtos.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    ContractController(ContractService contractService) {
        this.contractService = contractService;
        log.info("ContractController initialized");
    }

    @PostMapping
    public ResponseEntity<Contract> create(@Valid @RequestBody CreateContractDTO dto) {
        Contract newContract = contractService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newContract);
    }

    @GetMapping
    public ResponseEntity<List<Contract>> getAll() {
        List<Contract> allContracts = contractService.findAll();
        return ResponseEntity.ok(allContracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getById(@PathVariable Integer id) {
        Contract foundContract = contractService.findById(id);
        return ResponseEntity.ok(foundContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        contractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
