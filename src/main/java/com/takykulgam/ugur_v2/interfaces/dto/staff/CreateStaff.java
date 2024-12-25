package com.takykulgam.ugur_v2.interfaces.dto.staff;


import com.takykulgam.ugur_v2.applications.boundaries.input.staff.InputStaffCreate;
import reactor.core.publisher.Mono;

public record CreateStaff(String name, String password, boolean isAdmin) {
    public Mono<InputStaffCreate> toInput() {
        return Mono.just(new InputStaffCreate(name, password, isAdmin));
    }
}
