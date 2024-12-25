package com.takykulgam.ugur_v2.interfaces.mappers;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StopEntity;

public class EntityOutputStopMapper {

    public static OutputStop toDto(StopEntity entity) {
        return new OutputStop(
                entity.getId(),
                entity.getName(),
                entity.getLocation().getX(),
                entity.getLocation().getY(),
                entity.getCityId());
    }
}
