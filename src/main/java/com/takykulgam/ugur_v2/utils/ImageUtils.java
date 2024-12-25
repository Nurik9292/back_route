package com.takykulgam.ugur_v2.utils;

import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class ImageUtils {

    private static final List<String> SUPPORTED_FORMATS = Arrays.asList("png", "jpg", "jpeg");


    public static Mono<String> extractBase64(String image) {
        if (image == null || !image.startsWith("data:image/")) {
            return Mono.error(new CoreException("Invalid image format"));
        }

        String format = extractFormat(image);
        if (!isSupportedFormat(format)) {
            return Mono.error(new CoreException("Unsupported image format: " + format));
        }

        int base64StartIndex = image.indexOf(",") + 1;
        if (base64StartIndex == 0) {
            return Mono.error(new CoreException("Invalid Base64 format"));
        }

        return Mono.just(image.substring(base64StartIndex));
    }


    private static String extractFormat(String image) {
        int startIndex = image.indexOf("/") + 1;
        int endIndex = image.indexOf(";");
        if (startIndex <= 0 || endIndex <= startIndex) {
            throw new CoreException("Invalid image format");
        }
        return image.substring(startIndex, endIndex);
    }


    private static boolean isSupportedFormat(String format) {
        return SUPPORTED_FORMATS.contains(format.toLowerCase());
    }
}
