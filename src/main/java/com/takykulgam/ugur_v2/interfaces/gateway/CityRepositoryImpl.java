package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.domain.gateways.CityRepository;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.CityEntity;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.R2dbcCityRepository;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputCity;
import com.takykulgam.ugur_v2.interfaces.mappers.entityOutput.EntityOutputCityMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CityRepositoryImpl implements CityRepository {

    private final R2dbcCityRepository repository;
    private final EntityProcessor<CityEntity> processor;
    private final EntityOutputCityMapper outputCityMapper;

    public CityRepositoryImpl(R2dbcCityRepository repository,
                              EntityProcessor<CityEntity> processor,
                              EntityOutputCityMapper outputCityMapper) {
        this.repository = repository;
        this.processor = processor;
        this.outputCityMapper = outputCityMapper;
    }

    @Override
    public Flux<OutputCity> findAll() {
        return repository.findAll().map(outputCityMapper::toDto);
    }

    @Override
    public Mono<OutputCity> findById(long id) {
        return repository.findById(id).map(outputCityMapper::toDto);
    }

    @Override
    public Mono<OutputCity> save(String title) {
        return Mono
                .just(new CityEntity(title))
                .doOnNext(processor::preprocessBeforeSave)
                .flatMap(repository::save)
                .map(outputCityMapper::toDto);
    }

    @Override
    public Mono<OutputCity> update(long id, String title) {
        return repository.findById(id)
                .doOnNext(processor::preprocessBeforeUpdate)
                .flatMap(entity -> {
                    entity.setTitle(title);
                    return repository.save(entity);
                })
                .map(outputCityMapper::toDto);
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return repository.deleteById(id);
    }
}
