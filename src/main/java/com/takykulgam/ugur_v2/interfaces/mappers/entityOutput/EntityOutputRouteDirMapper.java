package com.takykulgam.ugur_v2.interfaces.mappers.entityOutput;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRouteDirection;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteDirectionEntity;

public class EntityOutputRouteDirMapper {

    public OutputRouteDirection toDto(RouteDirectionEntity routeDirectionEntity) {
        return new OutputRouteDirection(
                routeDirectionEntity.getId(),
                routeDirectionEntity.getDirection(),
                routeDirectionEntity.getRouteId()
        );
    }
}
