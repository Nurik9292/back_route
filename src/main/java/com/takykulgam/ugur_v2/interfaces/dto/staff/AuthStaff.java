package com.takykulgam.ugur_v2.interfaces.dto.staff;

import com.takykulgam.ugur_v2.applications.iteractor.staff.AuthStaffLoginCase;

public record AuthStaff(String name, String password) {
    public AuthStaffLoginCase.Input toInput() {
        return new AuthStaffLoginCase.Input(name, password);
    }
}
