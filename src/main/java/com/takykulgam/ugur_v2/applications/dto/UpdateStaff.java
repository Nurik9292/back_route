package com.takykulgam.ugur_v2.applications.dto;


import com.takykulgam.ugur_v2.applications.iteractor.staff.StaffUpdateCase;

public record UpdateStaff(String name, String password, boolean isAdmin) {
    public StaffUpdateCase.Input toInput(long id) {
        return new StaffUpdateCase.Input(id, name, password, isAdmin);
    }
}
