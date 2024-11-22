package com.takykulgam.ugur_v2.interfaces.mappers;

import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;

public class InputStaffToStaffEntityMapper {

    public static StaffEntity toEntity(InputStaff inputStaff) {
         return new StaffEntity(inputStaff.getName(), inputStaff.getPassword(), inputStaff.isAdmin());
    }

    public static InputStaff toInputStaff(StaffEntity staffEntity) {
        return new InputStaff(staffEntity.getName(), staffEntity.getPassword());
    }
}
