package com.takykulgam.ugur_v2.core.domain.gateways;

import com.takykulgam.ugur_v2.interfaces.dto.stop.OutputStop;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StopRepository {

    Mono<OutputStop> findById(long id);
    Mono<OutputStop> save(String title, double x, double y, long cityId);
    Mono<OutputStop> update(long id, String title, double x, double y, long cityId);
    Mono<Void> delete(long id);
    Flux<OutputStop> findAll();
    Mono<Long> count();
}
