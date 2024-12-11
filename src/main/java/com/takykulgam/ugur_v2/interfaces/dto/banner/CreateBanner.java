package com.takykulgam.ugur_v2.interfaces.dto.banner;

import com.takykulgam.ugur_v2.applications.iteractor.banner.BannerCreateUseCase;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public record CreateBanner(String banner) implements Serializable {
    public Mono<BannerCreateUseCase.Input> toInput() {
        return Mono.just(new BannerCreateUseCase.Input(banner));
    }
}
