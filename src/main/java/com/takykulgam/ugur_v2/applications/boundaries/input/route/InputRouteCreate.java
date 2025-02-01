package com.takykulgam.ugur_v2.applications.boundaries.input.route;

import com.takykulgam.ugur_v2.applications.boundaries.dto.PointModel;
import com.takykulgam.ugur_v2.domain.entities.Route;
import com.takykulgam.ugur_v2.domain.entities.RouteDirection;
import com.takykulgam.ugur_v2.domain.values.Point;

import java.util.List;

public record InputRouteCreate(
        String name,
        int number,
        int interval,
        List<PointModel> toPoints,
        List<PointModel> fromPoints,
        List<Long> toStops,
        List<Long> fromStops,
        long cityId
        ) {

    public Route toEntity() {
        return new Route(
                name,
                number,
                interval,
                new RouteDirection("to", toPoints.stream().map(this::toPointValue).toList()),
                new RouteDirection("from", fromPoints.stream().map(this::toPointValue).toList()),
                toStops,
                fromStops,
                cityId
        );
    }

    private Point toPointValue(PointModel point) {
        return new Point(point.lat(), point.lng());
    }
}
