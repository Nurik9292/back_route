package com.takykulgam.ugur_v2.core.domain.entities;

import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.utils.ImageUtils;

import java.util.Base64;

public record Banner(String banner) {

    private static final int MAX_BANNER_SIZE_BYTES = 1024 * 1024;

    public void validateBanner() {
        if (banner == null || banner.isBlank())
            throw new CoreException("Banner cannot be null or empty");

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(ImageUtils.extractBase64(banner).block());
            if (decodedBytes.length > MAX_BANNER_SIZE_BYTES)
                throw new CoreException("Banner size exceeds the maximum limit of 1MB");

        } catch (IllegalArgumentException e) {
            throw new CoreException("Invalid Base64 format for banner");
        }
    }
}
