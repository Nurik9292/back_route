package com.takykulgam.ugur_v2.interfaces.dto.banner;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputBannerCreate;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public record CreateBanner(String banner) implements Serializable {
    public Mono<InputBannerCreate> toInput() {
        return Mono.just(new InputBannerCreate(banner));
    }
}
