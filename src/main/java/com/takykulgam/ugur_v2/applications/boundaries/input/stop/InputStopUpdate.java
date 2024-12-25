package com.takykulgam.ugur_v2.applications.boundaries.input.stop;


import com.takykulgam.ugur_v2.applications.boundaries.dto.PointModel;
import com.takykulgam.ugur_v2.domain.entities.Stop;
import com.takykulgam.ugur_v2.domain.values.Point;
import reactor.core.publisher.Mono;

public record InputStopUpdate(long id, String name, PointModel location, long cityId) {
    public Mono<Stop> toEntity() {
        return Mono.just(new Stop(id, name, new Point(location.lat(), location.lng()), cityId));
    }
}
