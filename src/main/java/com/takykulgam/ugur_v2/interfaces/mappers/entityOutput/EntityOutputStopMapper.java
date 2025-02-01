package com.takykulgam.ugur_v2.interfaces.mappers.entityOutput;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStopForRoute;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StopEntity;

public class EntityOutputStopMapper {

    public OutputStop toDto(StopEntity entity) {
        return new OutputStop(
                entity.getId(),
                entity.getName(),
                entity.getLocation().getX(),
                entity.getLocation().getY(),
                entity.getCityId());
    }


    public OutputStopForRoute toDtoRoute(StopEntity entity) {
        return new OutputStopForRoute(
                entity.getId(),
                entity.getName());
    }
}
