package com.takykulgam.ugur_v2.interfaces.mappers;

import com.takykulgam.ugur_v2.interfaces.dto.city.OutputCity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.CityEntity;

public class EntityOutputCityMapper {

    public static OutputCity toDto(CityEntity entity) {
        return new OutputCity(entity.getId(), entity.getTitle());
    }
}
