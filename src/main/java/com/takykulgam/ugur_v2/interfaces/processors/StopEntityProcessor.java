package com.takykulgam.ugur_v2.interfaces.processors;

import com.takykulgam.ugur_v2.applications.iteractor.geo.PointCreateUseCase;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.applications.processors.GeoProcessor;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StopEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class StopEntityProcessor implements EntityProcessor<StopEntity>, GeoProcessor<StopEntity> {

    private final PointCreateUseCase pointCreateUseCase;

    public StopEntityProcessor(PointCreateUseCase pointCreateUseCase) {
        this.pointCreateUseCase = pointCreateUseCase;
    }

    @Override
    public void preprocessBeforeSave(StopEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
    }

    @Override
    public void preprocessBeforeUpdate(StopEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public Mono<Void> preprocessGeoBeforeSave(StopEntity entity, double x, double y) {
        return pointCreateUseCase.execute(Mono.just(new PointCreateUseCase.Input(x, y)))
                .result()
                .doOnNext(entity::setLocation)
                .then();
    }

    @Override
    public Mono<Void> preprocessGeoBeforeUpdate(StopEntity entity, double x, double y) {
        return pointCreateUseCase.execute(Mono.just(new PointCreateUseCase.Input(x, y)))
                .result()
                .doOnNext(entity::setLocation)
                .then();
    }
}
