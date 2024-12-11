package com.takykulgam.ugur_v2.applications.iteractor.image;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.ImageRepository;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public class DeleteImageService implements GenericUseCase<Mono<DeleteImageService.Input>, DeleteImageService.Output> {

    private final ImageRepository imageRepository;

    public DeleteImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request
                .flatMap(input -> imageRepository.delete(input.image, input.dir)));
    }

    public record Input(String image, Path dir) { }

    public record Output(Mono<Void> result) {}
}
