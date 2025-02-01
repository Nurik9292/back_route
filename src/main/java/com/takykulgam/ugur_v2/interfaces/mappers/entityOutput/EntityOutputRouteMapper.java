package com.takykulgam.ugur_v2.interfaces.mappers.entityOutput;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRoute;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteEntity;

public class EntityOutputRouteMapper {

    public OutputRoute toDto(RouteEntity routeEntity) {
        return new OutputRoute(
                routeEntity.getId(),
                routeEntity.getName(),
                "to",
                null,
                new OutputRoute.City(1, "test")
        );
    }
}
