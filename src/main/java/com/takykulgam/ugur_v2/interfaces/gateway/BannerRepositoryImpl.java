package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.BannerEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcBannerRepository;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputBanner;
import com.takykulgam.ugur_v2.interfaces.mappers.EntityOutputBannerMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BannerRepositoryImpl implements BannerRepository {

    private final R2dbcBannerRepository repository;
    private final EntityProcessor<BannerEntity> bannerEntityProcessor;

    public BannerRepositoryImpl(R2dbcBannerRepository repository, EntityProcessor<BannerEntity> bannerEntityProcessor) {
        this.repository = repository;
        this.bannerEntityProcessor = bannerEntityProcessor;
    }

    @Override
    public Flux<OutputBanner> findAll() {
        return repository.findAll().map(EntityOutputBannerMapper::toDto);
    }

    @Override
    public Mono<OutputBanner> findById(long id) {
        return repository.findById(id).map(EntityOutputBannerMapper::toDto);
    }

    @Override
    public Mono<OutputBanner> store(String banner) {
        return Mono
                .just(new BannerEntity(banner))
                .doOnNext(bannerEntityProcessor::preprocessBeforeSave)
                .flatMap(repository::save)
                .map(EntityOutputBannerMapper::toDto);
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteByImageUrl(String imageUrl) {
        return repository.deleteByImageUrl(imageUrl);
    }
}
