package com.takykulgam.ugur_v2.core.domain.gateways;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public interface ImageRepository {

    Mono<String> save(String image,  Path path);
    Mono<String> save(FilePart image, Path path);
    Mono<Void> delete(String image, Path path);
}
