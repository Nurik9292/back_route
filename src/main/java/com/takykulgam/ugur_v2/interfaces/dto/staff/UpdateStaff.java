package com.takykulgam.ugur_v2.interfaces.dto.staff;


import com.takykulgam.ugur_v2.applications.boundaries.input.staff.InputStaffUpdate;
import reactor.core.publisher.Mono;

public record UpdateStaff(String name, String password, boolean isAdmin) {
    public Mono<InputStaffUpdate> toInput(long id) {
        return Mono.just(new InputStaffUpdate(id, name, password, isAdmin));
    }
}
