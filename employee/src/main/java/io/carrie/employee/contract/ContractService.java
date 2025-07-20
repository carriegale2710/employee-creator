package io.carrie.employee.contract;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import io.carrie.employee.common.exceptions.NotFoundException;
import io.carrie.employee.common.utils.PatchUtils;
import io.carrie.employee.contract.dtos.CreateContractDTO;
import io.carrie.employee.contract.dtos.UpdateContractDTO;

@Service
public class ContractService {

    private ContractRepository contractRepository;
    private ModelMapper modelMapper;

    ContractService(ContractRepository contractRepository, ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
    }

    public List<Contract> findAll() {
        return this.contractRepository.findAll();
    }

    public Contract create(CreateContractDTO dto) throws IllegalArgumentException {
        Contract created = modelMapper.map(dto, Contract.class);
        return this.contractRepository.save(created);
    }

    public boolean deleteById(Integer id) {
        this.findById(id);
        this.contractRepository.deleteById(id);
        return true;
    }

    public Contract updateById(Integer id, UpdateContractDTO dto) {
        Contract found = findById(id);
        PatchUtils.patchContract(found, dto);
        return this.contractRepository.save(found);
        // you do not need to delete the old record - jpa/spring does it for you
    }

    public Contract findById(Integer id) throws NotFoundException {
        Optional<Contract> result = this.contractRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Contract with id " + id + " does not exist");
        }
        return result.get();
    }

}
