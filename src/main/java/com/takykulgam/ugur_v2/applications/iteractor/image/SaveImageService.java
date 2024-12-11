package com.takykulgam.ugur_v2.applications.iteractor.image;

import com.takykulgam.ugur_v2.core.domain.gateways.ImageRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

@Log4j2
public class SaveImageService implements GenericUseCase<Mono<SaveImageService.Image<?>>, SaveImageService.Output> {

    private final ImageRepository imageRepository;

    public SaveImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Output execute(Mono<Image<?>> request) {
        return new Output(
                request.flatMap(this::processImage)
                        .doOnError(this::logError)
        );
    }

    private Mono<String> processImage(Image<?> input) {
        return input.saveImage(imageRepository)
                .doOnSuccess(savedPath -> logInfo("Image saved to path:" + savedPath))
                .doOnError(this::logError);
    }

    private void logInfo(String message) {
        log.info(message);
    }

    private void logError(Throwable e) {
        log.error("Error: {}", e.getMessage());
    }

    public interface Image<T> {
        Mono<String> saveImage(ImageRepository imageRepository);

        Path dir();
    }

    public record InputFilePart(FilePart image, Path dir) implements Image<FilePart> {

        @Override
        public Mono<String> saveImage(ImageRepository imageRepository) {
            return imageRepository.save(image, dir());
        }
    }

    public record InputBase64(String image, Path dir) implements Image<String> {

        @Override
        public Mono<String> saveImage(ImageRepository imageRepository) {
            System.out.println(image);
            return imageRepository.save(image, dir());
        }
    }

    public record Output(Mono<String> path) {}
}
