package com.takykulgam.ugur_v2.interfaces.processors;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteEntity;

import java.time.LocalDateTime;

public class RouteEntityProcessor implements EntityProcessor<RouteEntity> {

    @Override
    public void preprocessBeforeSave(RouteEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
    }

    @Override
    public void preprocessBeforeUpdate(RouteEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }

}
