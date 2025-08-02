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
    public ResponseEntity<ResponseContractDTO> create(@Valid @RequestBody CreateContractDTO dto) {
        Contract contract = contractService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseContractDTO.fromEntity(contract));
    }

    @GetMapping
    public ResponseEntity<List<ResponseContractDTO>> getAll() {
        List<Contract> allContracts = contractService.findAll();
        List<ResponseContractDTO> response = allContracts.stream()
                .map(ResponseContractDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ResponseContractDTO>> getByEmployeeId(@PathVariable Integer employeeId) {
        List<Contract> contracts = contractService.findAllByEmployeeId(employeeId);
        List<ResponseContractDTO> response = contracts.stream()
                .map(ResponseContractDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseContractDTO> getById(@PathVariable Integer id) {
        Contract foundContract = contractService.findById(id);
        return ResponseEntity.ok(ResponseContractDTO.fromEntity(foundContract));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        contractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
