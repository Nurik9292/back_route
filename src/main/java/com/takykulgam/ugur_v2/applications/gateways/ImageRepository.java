package com.takykulgam.ugur_v2.applications.gateways;

import reactor.core.publisher.Mono;

public interface ImageRepository {

    Mono<String> save(String image);
}
