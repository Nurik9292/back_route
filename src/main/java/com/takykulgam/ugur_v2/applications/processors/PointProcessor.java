package com.takykulgam.ugur_v2.applications.processors;

import reactor.core.publisher.Mono;

public interface PointProcessor<T> {

    Mono<Void> preprocessGeoBeforeSave(T entity, double x, double y);
    Mono<Void> preprocessGeoBeforeUpdate(T entity, double x, double y);
}
