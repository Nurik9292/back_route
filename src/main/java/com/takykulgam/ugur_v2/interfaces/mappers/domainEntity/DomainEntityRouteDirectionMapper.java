package com.takykulgam.ugur_v2.interfaces.mappers.domainEntity;

import com.takykulgam.ugur_v2.domain.entities.Domain;
import com.takykulgam.ugur_v2.domain.entities.RouteDirection;
import com.takykulgam.ugur_v2.domain.values.Point;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteDirectionEntity;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import java.util.List;

public class DomainEntityRouteDirectionMapper {

    private final GeometryFactory geometryFactory;

    public DomainEntityRouteDirectionMapper(GeometryFactory geometryFactory) {
        this.geometryFactory = geometryFactory;
    }

    public RouteDirectionEntity toEntity(Domain domain, long routeId) {
        RouteDirection routeDirection = (RouteDirection) domain;
        RouteDirectionEntity routeDirectionEntity = new RouteDirectionEntity();
        routeDirectionEntity.setRouteId(routeId);
        routeDirectionEntity.setDirection(routeDirection.direction());
        routeDirectionEntity.setPath(createLineString(routeDirection.path()));
        return routeDirectionEntity;
    }

    private LineString createLineString(List<Point> path) {
        Coordinate[] coordinates = path.stream()
                .map(point -> new Coordinate(point.x(), point.y()))
                .toArray(Coordinate[]::new);

        return geometryFactory.createLineString(coordinates);
    }
}
