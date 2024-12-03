package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.gateways.ImageRepository;
import com.takykulgam.ugur_v2.infrastructure.storage.FileSystem;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.nio.file.Paths;
import java.util.Base64;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private final FileSystem fileSystem;

    public ImageRepositoryImpl(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public Mono<String> save(String image) {
        return  fileSystem
                .store(Base64.getDecoder().decode(image.substring("data:image/png;base64,".length())), Paths.get("avatar"))
                .flatMap(path -> Mono.just(path.getFileName().toString()));
    }
}
