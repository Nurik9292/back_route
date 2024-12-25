package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputRoute;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRoute;
import com.takykulgam.ugur_v2.domain.gateways.RouteRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RouteRepositoryImpl implements RouteRepository {


    @Override
    public Flux<OutputRoute> findAll() {
        return null;
    }

    @Override
    public Mono<OutputRoute> store(InputRoute inputRoute) {
        return null;
    }
}
