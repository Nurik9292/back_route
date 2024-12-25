package com.takykulgam.ugur_v2.applications.boundaries.input.staff;

import com.takykulgam.ugur_v2.domain.entities.Staff;

public record InputMe(long id, String name, String currentPassword, String password, String avatar) {
    public Staff toEntity() {
        return new Staff(name, password, avatar, true);
    }
}
