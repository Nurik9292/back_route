package com.takykulgam.ugur_v2.core.domain.gateways;

import com.takykulgam.ugur_v2.core.boundaries.output.OutputRoute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RouteRepository {

    Flux<OutputRoute> findAll();
    Mono<OutputRoute> store(String name);
}
