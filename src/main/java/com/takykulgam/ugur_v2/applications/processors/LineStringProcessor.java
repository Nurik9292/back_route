package com.takykulgam.ugur_v2.applications.processors;

import reactor.core.publisher.Mono;

public interface LineStringProcessor<T> {

//    Mono<Void> preprocessLineBeforeSave(T entity, );
    Mono<Void> preprocessLineBeforeUpdate(T entity, double x, double y);
}
