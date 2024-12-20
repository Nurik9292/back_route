package com.takykulgam.ugur_v2.core.domain.gateways;

import com.takykulgam.ugur_v2.core.boundaries.output.OutputCity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityRepository {

    Flux<OutputCity> findAll();
    Mono<OutputCity> findById(long id);
    Mono<OutputCity> save(String title);
    Mono<OutputCity> update(long id, String title);
    Mono<Void> deleteById(long id);
}
