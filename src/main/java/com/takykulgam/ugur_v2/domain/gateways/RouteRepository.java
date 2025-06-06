package com.takykulgam.ugur_v2.domain.gateways;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputRoute;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRoute;
import com.takykulgam.ugur_v2.domain.entities.Domain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RouteRepository {

    Flux<OutputRoute> findAll();
    Mono<OutputRoute> store(Domain domain);
}
