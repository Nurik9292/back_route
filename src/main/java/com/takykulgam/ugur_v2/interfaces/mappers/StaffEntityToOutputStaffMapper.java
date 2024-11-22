package com.takykulgam.ugur_v2.interfaces.mappers;

import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;

public class StaffEntityToOutputStaffMapper {

    public static OutputStaff toOutputStaff(StaffEntity staffEntity) {
        return new OutputStaff(staffEntity.getId(), staffEntity.getName(), staffEntity.getPassword(), staffEntity.isAdmin());
    }

}
