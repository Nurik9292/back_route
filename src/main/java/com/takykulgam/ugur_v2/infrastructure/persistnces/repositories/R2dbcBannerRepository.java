package com.takykulgam.ugur_v2.infrastructure.persistnces.repositories;

import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.BannerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface R2dbcBannerRepository extends R2dbcRepository<BannerEntity, Long> {

    Mono<Void> deleteByImageUrl(String imageUrl);
}
