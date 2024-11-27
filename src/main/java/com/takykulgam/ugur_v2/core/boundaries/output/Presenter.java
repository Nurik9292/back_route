package com.takykulgam.ugur_v2.core.boundaries.output;

import reactor.core.publisher.Mono;

public interface Presenter<I,O> {
    O getResponse();
    Mono<Void> present(boolean success, I item);
}
