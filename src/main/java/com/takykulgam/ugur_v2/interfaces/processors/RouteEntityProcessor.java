package com.takykulgam.ugur_v2.interfaces.processors;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.applications.processors.PointProcessor;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.RouteEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class RouteEntityProcessor implements EntityProcessor<RouteEntity>, PointProcessor<RouteEntity> {

    @Override
    public void preprocessBeforeSave(RouteEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
    }

    @Override
    public void preprocessBeforeUpdate(RouteEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public Mono<Void> preprocessGeoBeforeSave(RouteEntity entity, double x, double y) {
        return null;
    }

    @Override
    public Mono<Void> preprocessGeoBeforeUpdate(RouteEntity entity, double x, double y) {
        return null;
    }
}
