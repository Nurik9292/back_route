package com.takykulgam.ugur_v2.interfaces.mappers;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputCity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.CityEntity;

public class EntityOutputCityMapper {

    public static OutputCity toDto(CityEntity entity) {
        return new OutputCity(entity.getId(), entity.getTitle());
    }
}
