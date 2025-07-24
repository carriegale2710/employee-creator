package io.carrie.employee.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import io.carrie.employee.contract.dtos.CreateContractDTO;

public class ContractServiceTest {

    // NOTE (just testing the service works properly)
    // providing fake implementation for control data comparison
    // the data is not based on dependencies
    // won't work in e2e tests

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ModelMapper modelMapper;

    @Spy // call existing or mock
    @InjectMocks
    private ContractService contractService;

    @BeforeEach // setup your mock data for testing
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll_CallsFindAllOnRepo() {
        this.contractService.findAll();
        verify(this.contractRepository).findAll();
    }

    @Test
    public void findById_CallsFindByIdOnRepo() {
        Contract contract = new Contract();
        when(this.contractRepository.findById(1)).thenReturn(java.util.Optional.of(contract));
        this.contractService.findById(1);
        verify(this.contractRepository).findById(1);
    }

    @Test
    public void deleteById_CallsDeleteByIdOnRepo() {
        doReturn(new Contract()).when(contractService).findById(1);
        this.contractService.deleteById(1);
        verify(this.contractRepository).deleteById(1);
    }

    @Test
    public void create_CallsSaveOnRepo() {
        // Arrange- mock what should return (control vs mock data)
        Contract contract = new Contract();
        CreateContractDTO data = new CreateContractDTO();
        when(this.modelMapper.map(data, Contract.class)).thenReturn(contract);
        when(this.contractRepository.save(any(Contract.class))).thenReturn(contract);
        // Act -run the method
        Contract result = this.contractService.create(data);
        // Assert - check save method called by repo
        verify(this.contractRepository).save(contract);
        // Assert -check data
        assertNotNull(result);
        assertEquals(contract, result);
    }

}
