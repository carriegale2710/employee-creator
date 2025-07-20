package io.carrie.employee.common.utils;

import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.dtos.UpdateEmployeeDTO;

//NOTE - this lets us skip any fields that don't need changes

public class PatchUtils {
    public static void patchEmployee(Employee target, UpdateEmployeeDTO source) {
        if (source.getFirstName() != null) {
            target.setFirstName(source.getFirstName());
        }
        if (source.getLastName() != null) {
            target.setLastName(source.getLastName());
        }
        if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }
    }
}
