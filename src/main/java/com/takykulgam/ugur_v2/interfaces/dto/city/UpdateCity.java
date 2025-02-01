package com.takykulgam.ugur_v2.interfaces.dto.city;

import com.takykulgam.ugur_v2.applications.boundaries.input.city.InputCityUpdate;
import reactor.core.publisher.Mono;

public record UpdateCity(String title) {
    public Mono<InputCityUpdate> toInput(long id) {
        return Mono.just(new InputCityUpdate(id, title));
    }
}
