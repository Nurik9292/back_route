package com.takykulgam.ugur_v2.interfaces.dto.staff;

import com.takykulgam.ugur_v2.applications.boundaries.input.staff.InputMe;
import reactor.core.publisher.Mono;

public record MeStaff(long id, String name, String currentPassword, String newPassword, String avatar) {
    public Mono<InputMe> toInput() {
        return Mono.just(new InputMe(id, name, currentPassword, newPassword, avatar));
    }
}
