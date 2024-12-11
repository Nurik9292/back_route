package com.takykulgam.ugur_v2.interfaces.processors;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.CityEntity;

import java.time.LocalDateTime;

public class CityEntityProcessor implements EntityProcessor<CityEntity> {

    @Override
    public void preprocessBeforeSave(CityEntity entity) {
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public void preprocessBeforeUpdate(CityEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
