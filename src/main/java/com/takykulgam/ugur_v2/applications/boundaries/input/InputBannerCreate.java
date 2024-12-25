package com.takykulgam.ugur_v2.applications.boundaries.input;

import com.takykulgam.ugur_v2.domain.entities.Banner;

public record InputBannerCreate(String banner) {
    public Banner toBanner() {
        return new Banner(banner);
    }
}
