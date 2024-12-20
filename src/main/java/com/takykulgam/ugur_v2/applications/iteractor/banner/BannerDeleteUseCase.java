package com.takykulgam.ugur_v2.applications.iteractor.banner;

import com.takykulgam.ugur_v2.applications.iteractor.image.DeleteImageService;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.utils.ImagePathUtils;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;


@Log4j2
public class BannerDeleteUseCase implements GenericUseCase<Mono<BannerDeleteUseCase.Input>, BannerDeleteUseCase.Output> {

    private final BannerRepository bannerRepository;
    private final DeleteImageService deleteImageService;

    public BannerDeleteUseCase(BannerRepository bannerRepository,
                               DeleteImageService deleteImageService) {
        this.bannerRepository = bannerRepository;
        this.deleteImageService = deleteImageService;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request
                .flatMap(this::deleteImage)
                .flatMap(this::deleteBanner)
                .doOnSuccess(result -> log.info("Banner successfully deleted: {}", result))
                .doOnError(error -> log.error("Error deleting banner", error)));
    }


    private Mono<Input> deleteImage(Input input) {
        if (input.imagName != null) {
            return deleteImageService
                    .execute(Mono.just(new DeleteImageService.Input(input.imagName, ImagePathUtils.BANNER_PATH)))
                    .result()
                    .thenReturn(input);
        }
        return Mono.just(input);
    }


    private Mono<String> deleteBanner(Input input) {
        return bannerRepository.deleteByImageUrl(input.imagName)
                .then(Mono.just("Banner deleted successfully"));
    }

    public record Input(String imagName) {}

    public record Output(Mono<String> message) {}
}
