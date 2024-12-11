package com.takykulgam.ugur_v2.interfaces.processors;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.BannerEntity;

import java.time.LocalDateTime;

public class BannerEntityProcessor implements EntityProcessor<BannerEntity> {

    @Override
    public void preprocessBeforeSave(BannerEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
    }

    @Override
    public void preprocessBeforeUpdate(BannerEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
