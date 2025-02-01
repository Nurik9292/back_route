package com.takykulgam.ugur_v2.interfaces.dto.route;

import com.takykulgam.ugur_v2.applications.boundaries.dto.PointModel;
import com.takykulgam.ugur_v2.applications.boundaries.input.route.InputRouteCreate;
import reactor.core.publisher.Mono;

import java.util.List;

public record CreateRoute(
        String name,
        int number,
        int interval,
        List<PointModel> toPoints,
        List<PointModel> fromPoints,
        List<Long> toStops,
        List<Long> fromStops,
        long cityId) {
    public Mono<InputRouteCreate> toInput() {
        return Mono.just(new InputRouteCreate(name, number, interval, toPoints, fromPoints, toStops, fromStops, cityId));
    }
}
