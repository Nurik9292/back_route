package com.takykulgam.ugur_v2.interfaces.mappers;

import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.BannerEntity;
import com.takykulgam.ugur_v2.interfaces.dto.banner.OutputBanner;

public class EntityOutputBannerMapper {

    public static OutputBanner toDto(BannerEntity entity) {
        return new OutputBanner(entity.getId(), entity.getImageUrl());
    }
}
