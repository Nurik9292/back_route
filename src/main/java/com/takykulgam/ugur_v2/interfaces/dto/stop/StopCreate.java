package com.takykulgam.ugur_v2.interfaces.dto.stop;

import com.takykulgam.ugur_v2.applications.boundaries.dto.PointModel;
import com.takykulgam.ugur_v2.applications.boundaries.input.stop.InputStopCreate;
import reactor.core.publisher.Mono;

public record StopCreate(String name, double lat, double lng, long cityId) {
    public Mono<InputStopCreate> toInput() {
        return Mono.just(new InputStopCreate(name, new PointModel(lat, lng), cityId));
    }
}
