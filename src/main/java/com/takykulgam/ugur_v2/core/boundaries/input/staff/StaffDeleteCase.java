package com.takykulgam.ugur_v2.core.boundaries.input.staff;

import reactor.core.publisher.Mono;

public interface StaffDeleteCase {
    Mono<Void> execute(long id);
}
