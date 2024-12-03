package com.takykulgam.ugur_v2.applications.iteractor.image;

import com.takykulgam.ugur_v2.applications.gateways.ImageRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import reactor.core.publisher.Mono;

public class SaveImageService implements GenericUseCase<Mono<SaveImageService.Inout>, SaveImageService.Output> {

    private final ImageRepository imageRepository;

    public SaveImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Output execute(Mono<Inout> request) {
        return new Output(request.flatMap(input -> {
            System.out.println(input.image);
            return imageRepository.save(input.image);
        }));
    }

    public record Inout(String image) {}

    public record Output(Mono<String> path) {}
}
