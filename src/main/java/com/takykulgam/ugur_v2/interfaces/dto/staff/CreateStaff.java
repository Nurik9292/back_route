package com.takykulgam.ugur_v2.interfaces.dto.staff;


import com.takykulgam.ugur_v2.applications.iteractor.staff.StaffCreateCase;

public record CreateStaff(String name, String password, boolean isAdmin) {
    public StaffCreateCase.Input toInput() {
        return new StaffCreateCase.Input(name, password, isAdmin);
    }
}
