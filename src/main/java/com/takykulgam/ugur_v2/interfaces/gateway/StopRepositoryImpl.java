package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStopForRoute;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.domain.entities.Domain;
import com.takykulgam.ugur_v2.domain.gateways.StopRepository;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StopEntity;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.R2dbcStopRepository;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityStopMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.entityOutput.EntityOutputStopMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class StopRepositoryImpl implements StopRepository {

    private final R2dbcStopRepository stopRepository;
    private final EntityProcessor<StopEntity> processor;
    private final DomainEntityStopMapper domainEntityStopMapper;
    private final EntityOutputStopMapper entityOutputStopMapper;

    public StopRepositoryImpl(R2dbcStopRepository stopRepository,
                              EntityProcessor<StopEntity> processor,
                              DomainEntityStopMapper domainEntityStopMapper,
                              EntityOutputStopMapper entityOutputStopMapper) {
        this.stopRepository = stopRepository;
        this.processor = processor;
        this.domainEntityStopMapper = domainEntityStopMapper;
        this.entityOutputStopMapper = entityOutputStopMapper;
    }

    @Override
    public Mono<OutputStop> findById(long id) {
        return stopRepository.findById(id).map(entityOutputStopMapper::toDto);
    }

    @Override
    public Mono<OutputStop> save(Domain domain) {
        return Mono.just(domainEntityStopMapper.toEntity(domain))
                .doOnNext(processor::preprocessBeforeSave)
                .flatMap(stopRepository::save)
                .map(entityOutputStopMapper::toDto);
    }

    @Override
    public Mono<OutputStop> update(long id, Domain domain) {
        return stopRepository.findById(id)
                .map(entity -> {
                    StopEntity stop = domainEntityStopMapper.toEntity(domain);
                    entity.setName(Objects.isNull(stop.getName()) ? entity.getName() : stop.getName());
                    entity.setCityId(stop.getCityId());
                    return entity;
                })
                .doOnNext(processor::preprocessBeforeUpdate)
                .flatMap(stopRepository::save)
                .map(entityOutputStopMapper::toDto);
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return stopRepository.deleteById(id);
    }

    @Override
    public Flux<OutputStop> findAll() {
        return stopRepository.findAll().map(entityOutputStopMapper::toDto);
    }

    @Override
    public Flux<OutputStopForRoute> fetchAll() {
        return stopRepository.findAll().map(entityOutputStopMapper::toDtoRoute);
    }

    @Override
    public Mono<Long> count() {
        return stopRepository.count();
    }
}
