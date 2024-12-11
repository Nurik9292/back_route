package com.takykulgam.ugur_v2.applications.iteractor.city;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.CityRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class CityDeleteUseCase implements GenericUseCase<Mono<CityDeleteUseCase.Input>, CityDeleteUseCase.Output> {

    private final CityRepository cityRepository;


    public CityDeleteUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(
                request.flatMap(this::deleteCityById)
                        .doOnSuccess(result -> log.info("City successfully deleted: {}", result))
                        .doOnError(error -> log.error("Error deleting city", error))
        );
    }

    private Mono<String> deleteCityById(Input input) {
        return cityRepository.deleteById(input.id())
                            .then(Mono.just("City deleted successfully"));
    }


    public record Input(long id) {}

    public record Output(Mono<String> message) {}
}
