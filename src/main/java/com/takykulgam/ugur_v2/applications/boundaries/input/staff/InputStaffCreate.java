package com.takykulgam.ugur_v2.applications.boundaries.input.staff;

import com.takykulgam.ugur_v2.domain.entities.Staff;

public record InputStaffCreate(String name, String password, boolean isAdmin) {
    public Staff toEntity() {
        return new Staff(name, password, isAdmin);
    }
}
