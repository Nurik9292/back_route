package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.core.domain.gateways.ImageRepository;
import com.takykulgam.ugur_v2.infrastructure.storage.FileSystem;
import com.takykulgam.ugur_v2.utils.ImageUtils;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.util.Base64;

public class ImageRepositoryImpl implements ImageRepository {

    private final FileSystem fileSystem;

    public ImageRepositoryImpl(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public Mono<String> save(String image, Path dir) {
        return  ImageUtils.extractBase64(image)
                .flatMap(base64 -> fileSystem
                            .store(Base64.getDecoder().decode(base64), dir)
                            .flatMap(path -> Mono.just(path.getFileName().toString()))
                );
    }




    @Override
    public Mono<String> save(FilePart image, Path dir) {
        return  fileSystem
                .store(image, dir)
                .flatMap(path -> Mono.just(path.getFileName().toString()));
    }

    @Override
    public Mono<Void> delete(String image, Path path) {
        return fileSystem.delete(path.resolve(image).toString());
    }
}
