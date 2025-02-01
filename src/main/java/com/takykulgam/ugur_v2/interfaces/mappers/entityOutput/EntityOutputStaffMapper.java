package com.takykulgam.ugur_v2.interfaces.mappers.entityOutput;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StaffEntity;

public class EntityOutputStaffMapper {

    public  OutputStaff toDto(StaffEntity staffEntity) {
        return new OutputStaff(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getPassword(),
                staffEntity.isAdmin(),
                staffEntity.getAvatar());
    }
}
