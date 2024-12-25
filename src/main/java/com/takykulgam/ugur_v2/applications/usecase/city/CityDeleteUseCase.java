package com.takykulgam.ugur_v2.applications.usecase.city;

import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.CityRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class CityDeleteUseCase implements GenericUseCase<Mono<Long>, CityDeleteUseCase.Output> {

    private final CityRepository cityRepository;


    public CityDeleteUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<Long> request) {
        return new Output(
                request.flatMap(cityRepository::deleteById)
                        .then(Mono.just("City deleted successfully"))
                        .doOnSuccess(result -> log.info("City successfully deleted: {}", result))
                        .doOnError(error -> log.error("Error deleting city", error))
        );
    }

    public record Output(Mono<String> message) {}
}
