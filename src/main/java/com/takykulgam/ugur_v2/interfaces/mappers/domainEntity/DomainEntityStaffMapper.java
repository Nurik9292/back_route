package com.takykulgam.ugur_v2.interfaces.mappers.domainEntity;

import com.takykulgam.ugur_v2.domain.entities.Domain;
import com.takykulgam.ugur_v2.domain.entities.Staff;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StaffEntity;

public class DomainEntityStaffMapper {

    public StaffEntity toEntity(Domain domain) {
        Staff staff = (Staff) domain;
        return new StaffEntity(staff.getName(), staff.getPassword().value(), staff.isAdmin());
    }
}
