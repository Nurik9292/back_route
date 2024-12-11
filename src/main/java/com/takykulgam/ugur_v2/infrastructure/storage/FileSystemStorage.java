package com.takykulgam.ugur_v2.infrastructure.storage;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Log4j2
@Component
public class FileSystemStorage implements FileSystem {

    private final Path storagePath = Path.of("images");
    @Value("${base.url.staff}")
    private String baseUrl;

    public FileSystemStorage() {
        try {
            Files.createDirectories(storagePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create storage directory", e);
        }
    }

    @Override
    public Mono<Path> store(final byte[] bytes, final Path dir) {
        return Mono.fromCallable(() -> {
            Path targetDir = storagePath.resolve(dir);
            if (!Files.exists(targetDir))
                        Files.createDirectories(targetDir);

            String name = UUID.randomUUID() + ".png";
            return Files.write(targetDir.resolve(name), bytes, StandardOpenOption.CREATE_NEW);
        })
        .subscribeOn(Schedulers.boundedElastic())
        .onErrorMap(e -> new RuntimeException("Failed to store image", e))
        .doOnError(e ->  log.error("Ошибка при записи файла: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Path> store(FilePart image, final Path dir) {
        return Mono.fromCallable(() -> {
                    Path targetDir = storagePath.resolve(dir);
                    if (!Files.exists(targetDir))
                        Files.createDirectories(targetDir);

                    String name = UUID.randomUUID() + ".png";
                    return targetDir.resolve(name);
                })
                .flatMap(targetPath -> image.transferTo(targetPath.toFile())
                        .thenReturn(targetPath)
                )
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorMap(e -> new RuntimeException("Failed to store image", e))
                .doOnError(e -> log.error("Ошибка при записи файла: {}", e.getMessage(), e));
    }

    @Override
    public Mono<byte[]> load(final String fileName) {
        return Mono.fromCallable(() -> {
            Path path = storagePath.resolve(fileName);
            if (Files.exists(path)) return Files.readAllBytes(path);
            else throw  new IOException("File not found: " + fileName);
        })
        .subscribeOn(Schedulers.boundedElastic())
        .onErrorMap(e -> new RuntimeException("Failed to load image", e))
        .doOnError(e -> log.error("Ошибка при загрузке файла: {}", e.getMessage(), e));
    }

    @Override
    public Mono<String> loadLink(final String fileName) {
        return Mono.fromCallable(() -> {
            Path path = storagePath.resolve(fileName);
            if (Files.exists(path)) return baseUrl + fileName;
            else throw  new IOException("File not found: " + fileName);
        })
        .subscribeOn(Schedulers.boundedElastic())
        .onErrorMap(e -> new RuntimeException("Failed to generate image link", e))
        .doOnError(e -> log.error("Ошибка при генерации ссылки: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Resource> loadAsResource(final String fileName) {
        return Mono.fromCallable(() -> storagePath.resolve(fileName))
                .flatMap(file -> {
                    try {
                        Resource resource = new UrlResource(file.toUri());
                        if (resource.exists() && resource.isReadable()) {
                            return Mono.just(resource);
                        } else {
                            return Mono.error(new FileNotFoundException("Could not read file: " + fileName));
                        }
                    } catch (MalformedURLException e) {
                        return Mono.error(new FileNotFoundException("Could not read file: " + fileName));
                    }
                });
    }


    @Override
    public Mono<Void> delete(final String fileName) {
        return Mono.fromRunnable(() -> {
                    Path filePath = storagePath.resolve(fileName);
                    try {
                        if (Files.exists(filePath)) {
                            Files.delete(filePath);
                            log.info("File deleted successfully: {}", fileName);
                        } else {
                            log.warn("File not found for deletion: {}", fileName);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("Error deleting file: " + fileName, e);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error("Error deleting file: {}", e.getMessage(), e))
                .then();
    }

}
