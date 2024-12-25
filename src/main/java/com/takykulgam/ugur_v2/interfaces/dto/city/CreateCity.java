package com.takykulgam.ugur_v2.interfaces.dto.city;

import com.takykulgam.ugur_v2.applications.boundaries.input.stop.InputCityCreate;
import reactor.core.publisher.Mono;

public record CreateCity(String name) {
    public Mono<InputCityCreate> toInput() {
        return Mono.just(new InputCityCreate(name));
    };
}
