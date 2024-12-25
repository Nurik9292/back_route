package com.takykulgam.ugur_v2.domain.gateways;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputBanner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BannerRepository {

    Flux<OutputBanner> findAll();
    Mono<OutputBanner> findById(long id);
    Mono<OutputBanner> store(String banner);
    Mono<Void> deleteById(long id);
    Mono<Void> deleteByImageUrl(String imageUrl);
}
