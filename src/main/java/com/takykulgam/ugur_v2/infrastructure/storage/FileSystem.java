package com.takykulgam.ugur_v2.infrastructure.storage;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import org.springframework.core.io.Resource;

import java.nio.file.Path;

public interface FileSystem {

    Mono<Path> store(byte[] bytes, Path path);
    Mono<Path> store(FilePart image, final Path dir);
    Mono<byte[]> load(String fileName);
    Mono<String> loadLink(String fileName);
    Mono<Resource> loadAsResource(String fileName);
    Mono<Void> delete(String fileName);
}
