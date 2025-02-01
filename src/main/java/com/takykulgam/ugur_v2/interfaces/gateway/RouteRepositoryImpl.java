package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRoute;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.domain.entities.Domain;
import com.takykulgam.ugur_v2.domain.gateways.RouteRepository;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteEntity;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.R2dbcRouteRepository;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityRouteMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.entityOutput.EntityOutputRouteMapper;
import com.takykulgam.ugur_v2.interfaces.processors.RouteEntityProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RouteRepositoryImpl implements RouteRepository {

    private final R2dbcRouteRepository repository;
    private final DomainEntityRouteMapper routeDomainMapper;
    private final EntityProcessor<RouteEntity> processor;
    private final EntityOutputRouteMapper outputRouteMapper;

    public RouteRepositoryImpl(R2dbcRouteRepository repository,
                               DomainEntityRouteMapper routeDomainMapper,
                               EntityProcessor<RouteEntity> processor,
                               EntityOutputRouteMapper outputRouteMapper) {
        this.repository = repository;
        this.routeDomainMapper = routeDomainMapper;
        this.processor = processor;
        this.outputRouteMapper = outputRouteMapper;
    }

    @Override
    public Flux<OutputRoute> findAll() {
        return null;
    }

    @Override
    public Mono<OutputRoute> store(Domain domain) {
        return Mono.just(routeDomainMapper.toEntity(domain))
                .doOnNext(processor::preprocessBeforeSave)
                .flatMap(repository::save)
                .map(outputRouteMapper::toDto);
    }
}
