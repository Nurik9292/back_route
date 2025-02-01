package com.takykulgam.ugur_v2.interfaces.mappers.entityOutput;

import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.BannerEntity;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputBanner;

public class EntityOutputBannerMapper {

    public OutputBanner toDto(BannerEntity entity) {
        return new OutputBanner(entity.getId(), entity.getImageUrl());
    }
}
