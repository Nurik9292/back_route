package com.takykulgam.ugur_v2.applications.iteractor.banner;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputBanner;
import reactor.core.publisher.Mono;

public class GetBannerByIdUseCase implements GenericUseCase<Mono<GetBannerByIdUseCase.Input>, GetBannerByIdUseCase.Output> {

    private final BannerRepository bannerRepository;

    public GetBannerByIdUseCase(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request.flatMap(input -> bannerRepository.findById(input.id)));
    }

    public record Input(long id) { }

    public record Output(Mono<OutputBanner> result) { }
}
