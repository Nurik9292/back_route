package com.takykulgam.ugur_v2.interfaces.mappers.entityOutput;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputCity;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.CityEntity;

public class EntityOutputCityMapper {

    public OutputCity toDto(CityEntity entity) {
        return new OutputCity(entity.getId(), entity.getTitle());
    }
}
