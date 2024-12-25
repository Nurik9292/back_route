package com.takykulgam.ugur_v2.applications.usecase.banner;

import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.applications.usecase.image.DeleteImageService;
import com.takykulgam.ugur_v2.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.utils.ImagePathUtils;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;


@Log4j2
public class BannerDeleteUseCase implements GenericUseCase<Mono<String>, BannerDeleteUseCase.Output> {

    private final BannerRepository bannerRepository;
    private final DeleteImageService deleteImageService;

    public BannerDeleteUseCase(BannerRepository bannerRepository,
                               DeleteImageService deleteImageService) {
        this.bannerRepository = bannerRepository;
        this.deleteImageService = deleteImageService;
    }

    @Override
    public Output execute(Mono<String> request) {
        return new Output(request
                .flatMap(this::deleteImage)
                .flatMap(this::deleteBanner)
                .doOnSuccess(result -> log.info("Banner successfully deleted: {}", result))
                .doOnError(error -> log.error("Error deleting banner", error)));
    }


    private Mono<String> deleteImage(String input) {
        return  deleteImageService.execute(Mono.just(new DeleteImageService.Input(input, ImagePathUtils.BANNER_PATH)))
                .result()
                .thenReturn(input);
    }


    private Mono<String> deleteBanner(String input) {
        return bannerRepository.deleteByImageUrl(input)
                .then(Mono.just("Banner deleted successfully"));
    }


    public record Output(Mono<String> message) {}
}
