package com.takykulgam.ugur_v2.interfaces.processors;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteDirectionEntity;

import java.time.LocalDateTime;

public class RouteDirectionEntityProcessor implements EntityProcessor<RouteDirectionEntity> {


    public RouteDirectionEntityProcessor() {
    }

    @Override
    public void preprocessBeforeSave(RouteDirectionEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
    }

    @Override
    public void preprocessBeforeUpdate(RouteDirectionEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }


}
