package com.takykulgam.ugur_v2.applications.dto;


import com.takykulgam.ugur_v2.applications.iteractor.staff.StaffMeUseCase;

public record MeStaff(String name, String newPassword, String avatar) {
    public StaffMeUseCase.Input toInput() {
        return new StaffMeUseCase.Input(name, newPassword, avatar);
    }
}
