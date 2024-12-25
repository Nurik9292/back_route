package com.takykulgam.ugur_v2.applications.usecase.banner;

import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputBanner;
import reactor.core.publisher.Mono;

public class BannerGetByIdUseCase implements GenericUseCase<Mono<Long>, BannerGetByIdUseCase.Output> {

    private final BannerRepository bannerRepository;

    public BannerGetByIdUseCase(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Output execute(Mono<Long> request) {
        return new Output(request.flatMap(bannerRepository::findById));
    }

    public record Input(long id) { }

    public record Output(Mono<OutputBanner> result) { }
}
