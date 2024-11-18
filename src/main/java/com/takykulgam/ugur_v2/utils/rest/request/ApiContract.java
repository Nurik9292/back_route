package com.takykulgam.ugur_v2.utils.rest.request;

import reactor.core.publisher.Flux;

public interface ApiContract<T> {

    public Flux<T> execute();
}
