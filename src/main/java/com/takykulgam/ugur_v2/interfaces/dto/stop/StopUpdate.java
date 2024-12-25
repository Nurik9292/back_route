package com.takykulgam.ugur_v2.interfaces.dto.stop;

import com.takykulgam.ugur_v2.applications.boundaries.dto.PointModel;
import com.takykulgam.ugur_v2.applications.boundaries.input.stop.InputStopUpdate;
import reactor.core.publisher.Mono;

public record StopUpdate(String name, double lat, double lng, long cityId) {
    public Mono<InputStopUpdate> toInput(long id) {
        return Mono.just(new InputStopUpdate(id, name, new PointModel(lat, lng), cityId));
    }
}
