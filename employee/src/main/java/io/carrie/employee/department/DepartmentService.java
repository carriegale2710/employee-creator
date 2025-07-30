package io.carrie.employee.department;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.carrie.employee.common.exceptions.NotFoundException;
import io.carrie.employee.department.dtos.CreateDepartmentDTO;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department findByName(String name) {
        if (!departmentRepository.existsByName(name)) {
            throw new NotFoundException("Department with name '" + name + "' not found");
        }
        return departmentRepository.findByName(name);
    }

    public Department createDepartment(CreateDepartmentDTO dto) {
        String cleaned = dto.getName().toUpperCase();

        if (departmentRepository.existsByName(cleaned)) {
            throw new IllegalArgumentException(
                    "Department with name '" + cleaned + "' already exists");
        }

        Department department = new Department();
        department.setName(cleaned);

        return departmentRepository.save(department);
    }

}
