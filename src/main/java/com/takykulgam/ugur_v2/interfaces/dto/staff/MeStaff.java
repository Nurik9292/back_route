package com.takykulgam.ugur_v2.interfaces.dto.staff;


import com.takykulgam.ugur_v2.applications.iteractor.staff.StaffMeUseCase;

public record MeStaff(long id, String name, String currentPassword, String newPassword, String avatar) {
    public StaffMeUseCase.Input toInput() {
        return new StaffMeUseCase.Input(id, name, currentPassword, newPassword, avatar);
    }
}
