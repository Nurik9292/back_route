package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.applications.processors.PointProcessor;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.domain.gateways.StopRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StopEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcStopRepository;
import com.takykulgam.ugur_v2.interfaces.mappers.EntityOutputStopMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class StopRepositoryImpl implements StopRepository {

    private final R2dbcStopRepository stopRepository;
    private final EntityProcessor<StopEntity> processor;
    private final PointProcessor<StopEntity> geoProcessor;

    public StopRepositoryImpl(R2dbcStopRepository stopRepository,
                              EntityProcessor<StopEntity> processor,
                              PointProcessor<StopEntity> geoProcessor) {
        this.stopRepository = stopRepository;
        this.processor = processor;
        this.geoProcessor = geoProcessor;
    }

    @Override
    public Mono<OutputStop> findById(long id) {
        return stopRepository.findById(id).map(EntityOutputStopMapper::toDto);
    }

    @Override
    public Mono<OutputStop> save(final String title, final double x, final double y, final long cityId) {
        return Mono.just(new StopEntity(title, cityId))
                .doOnNext(processor::preprocessBeforeSave)
                .flatMap(stop -> geoProcessor.preprocessGeoBeforeSave(stop, x, y).thenReturn(stop))
                .flatMap(stopRepository::save)
                .map(EntityOutputStopMapper::toDto);
    }

    @Override
    public Mono<OutputStop> update(long id, String name, double x, double y, long cityId) {
        System.out.println(id);
        System.out.println(name);
        return stopRepository.findById(id)
                .map(entity -> {
                    entity.setName(Objects.isNull(name) ? entity.getName() : name);
                    entity.setCityId(cityId);
                    return entity;
                })
                .doOnNext(processor::preprocessBeforeUpdate)
                .flatMap(stop -> geoProcessor.preprocessGeoBeforeUpdate(stop, x, y).thenReturn(stop))
                .flatMap(stopRepository::save)
                .map(EntityOutputStopMapper::toDto);
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return stopRepository.deleteById(id);
    }

    @Override
    public Flux<OutputStop> findAll() {
        return stopRepository.findAll().map(EntityOutputStopMapper::toDto);
    }

    @Override
    public Mono<Long> count() {
        return stopRepository.count();
    }
}
