package com.takykulgam.ugur_v2.infrastructure.storage;

import reactor.core.publisher.Mono;
import org.springframework.core.io.Resource;

import java.nio.file.Path;

public interface FileSystem {

    Mono<Path> store(byte[] bytes, Path path);
    Mono<byte[]> load(String fileName);
    Mono<String> loadLink(String fileName);
    Mono<Resource> loadAsResource(String fileName);
}
