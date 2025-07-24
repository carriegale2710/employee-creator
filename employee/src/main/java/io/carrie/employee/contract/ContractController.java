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

import io.carrie.employee.contract.dtos.CreateContractDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private ContractService contractService;

    ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<Contract> create(@Valid @RequestBody CreateContractDTO dto) {
        Contract newContract = this.contractService.create(dto);
        return new ResponseEntity<>(newContract, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Contract>> getAll() {
        List<Contract> allContracts = this.contractService.findAll();
        return new ResponseEntity<>(allContracts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getById(@PathVariable Integer id) {
        Contract foundContract = this.contractService.findById(id);
        return new ResponseEntity<>(foundContract, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        this.contractService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
