package com.takykulgam.ugur_v2.interfaces.mappers;

import com.takykulgam.ugur_v2.interfaces.dto.staff.OutputStaff;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;

public class EntityOutPutStaffMapper {

    public static OutputStaff toDto(StaffEntity staffEntity) {
        return new OutputStaff(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getPassword(),
                staffEntity.isAdmin(),
                staffEntity.getAvatar());
    }
}
