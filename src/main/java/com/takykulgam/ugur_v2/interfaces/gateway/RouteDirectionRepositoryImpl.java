package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRouteDirection;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.domain.entities.Domain;
import com.takykulgam.ugur_v2.domain.gateways.RouteDirectionRepository;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteDirectionEntity;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.R2dbcRouteDirectionRepository;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityRouteDirectionMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.entityOutput.EntityOutputRouteDirMapper;
import reactor.core.publisher.Mono;

public class RouteDirectionRepositoryImpl implements RouteDirectionRepository {

    private final R2dbcRouteDirectionRepository repository;
    private final EntityProcessor<RouteDirectionEntity> routeDirectionProcessor;
    private final DomainEntityRouteDirectionMapper domainEntityRouteDirectionMapper;
    private final EntityOutputRouteDirMapper entityOutputRouteDirMapper;


    public RouteDirectionRepositoryImpl(R2dbcRouteDirectionRepository repository,
                                        EntityProcessor<RouteDirectionEntity> routeDirectionProcessor,
                                        DomainEntityRouteDirectionMapper domainEntityRouteDirectionMapper,
                                        EntityOutputRouteDirMapper entityOutputRouteDirMapper) {
        this.repository = repository;
        this.routeDirectionProcessor = routeDirectionProcessor;
        this.domainEntityRouteDirectionMapper = domainEntityRouteDirectionMapper;
        this.entityOutputRouteDirMapper = entityOutputRouteDirMapper;
    }

    @Override
    public Mono<OutputRouteDirection> store(Domain domain, Long routeId) {
        return Mono.just(domainEntityRouteDirectionMapper.toEntity(domain, routeId))
                .doOnNext(routeDirectionProcessor::preprocessBeforeSave)
                .flatMap(repository::save)
                .map(entityOutputRouteDirMapper::toDto);
    }
}
