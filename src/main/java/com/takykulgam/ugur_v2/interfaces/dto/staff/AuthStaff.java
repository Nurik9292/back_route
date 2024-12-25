package com.takykulgam.ugur_v2.interfaces.dto.staff;

import com.takykulgam.ugur_v2.applications.boundaries.input.staff.InputAuth;
import reactor.core.publisher.Mono;

public record AuthStaff(String name, String password) {
    public Mono<InputAuth> toInput() {
        return Mono.just(new InputAuth(name, password));
    }
}
