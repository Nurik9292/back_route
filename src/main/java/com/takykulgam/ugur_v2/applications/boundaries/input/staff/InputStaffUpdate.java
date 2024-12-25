package com.takykulgam.ugur_v2.applications.boundaries.input.staff;

import com.takykulgam.ugur_v2.domain.entities.Staff;

public record InputStaffUpdate(long id, String name, String password, boolean isAdmin) {
    public Staff toEntity() {
        return new Staff(id, name, password, isAdmin);
    }
}
