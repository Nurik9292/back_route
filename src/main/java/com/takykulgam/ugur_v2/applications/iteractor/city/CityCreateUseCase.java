package com.takykulgam.ugur_v2.applications.iteractor.city;

import com.takykulgam.ugur_v2.core.domain.entities.City;
import com.takykulgam.ugur_v2.interfaces.dto.city.OutputCity;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.CityRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class CityCreateUseCase implements GenericUseCase<Mono<CityCreateUseCase.Input>, CityCreateUseCase.Output> {

    private final CityRepository cityRepository;

    public CityCreateUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(
                request
                        .map(this::toEntity)
                        .doOnNext(city -> log.info("Validating city: {}", city))
                        .doOnNext(this::validateCity)
                        .flatMap(this::saveCity)
                        .doOnSuccess(result -> log.info("City successfully created: {}", result))
                        .doOnError(error -> log.error("Error creating city", error))
        );
    }

    private City toEntity(Input input) {
        return new City(input.title());
    }

    private void validateCity(City city) {
        city.validateTitle();
    }

    private Mono<OutputCity> saveCity(City city) {
        return cityRepository.save(city.getTitle());
    }

    public record Input(String title) {}

    public record Output(Mono<OutputCity> result) {}
}
