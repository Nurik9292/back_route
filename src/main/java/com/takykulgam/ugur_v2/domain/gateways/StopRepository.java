package com.takykulgam.ugur_v2.domain.gateways;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStopForRoute;
import com.takykulgam.ugur_v2.domain.entities.Domain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StopRepository {

    Mono<OutputStop> findById(long id);
    Mono<OutputStop> save(Domain domain);
    Mono<OutputStop> update(long id, Domain domain);
    Mono<Void> deleteById(long id);
    Flux<OutputStop> findAll();
    Flux<OutputStopForRoute> fetchAll();
    Mono<Long> count();
}
