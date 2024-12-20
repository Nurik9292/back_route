package com.takykulgam.ugur_v2.applications.iteractor.banner;

import com.takykulgam.ugur_v2.applications.iteractor.image.SaveImageService;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.entities.Banner;
import com.takykulgam.ugur_v2.core.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputBanner;
import com.takykulgam.ugur_v2.utils.ImagePathUtils;
import reactor.core.publisher.Mono;

public class BannerCreateUseCase implements GenericUseCase<Mono<BannerCreateUseCase.Input>, BannerCreateUseCase.Output> {

    private final BannerRepository bannerRepository;
    private final SaveImageService saveImageService;

    public BannerCreateUseCase(BannerRepository bannerRepository, SaveImageService saveImageService) {
        this.bannerRepository = bannerRepository;
        this.saveImageService = saveImageService;
    }


    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request
                .map(Input::toBanner)
                .doOnNext(Banner::validateBanner)
                .flatMap(this::saveImage)
                .flatMap(this::saveBanner));
    }

    private Mono<String> saveImage(Banner input) {
        return saveImageService.execute(Mono.just(input).map(this::base64ToInput)).path();
    }

    private Mono<OutputBanner> saveBanner(String saveImage) {
        return bannerRepository.store(saveImage);
    }

    private SaveImageService.InputBase64 base64ToInput(Banner banner) {
        return new SaveImageService.InputBase64(banner.banner(), ImagePathUtils.BANNER_PATH);
    }

    public record Input(String banner) {
        public Banner toBanner() {
            return new Banner(banner);
        }
    }

    public record Output(Mono<OutputBanner> result) {}
}
