package com.takykulgam.ugur_v2.applications.usecase.city;

import com.takykulgam.ugur_v2.applications.boundaries.input.city.InputCityCreate;
import com.takykulgam.ugur_v2.domain.entities.City;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputCity;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.CityRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class CityCreateUseCase implements GenericUseCase<Mono<InputCityCreate>, CityCreateUseCase.Output> {

    private final CityRepository cityRepository;

    public CityCreateUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<InputCityCreate> request) {
        return new Output(
                request
                        .map(InputCityCreate::toEntity)
                        .doOnNext(this::validateCity)
                        .flatMap(this::saveCity)
                        .doOnSuccess(result -> log.info("City successfully created: {}", result))
                        .doOnError(error -> log.error("Error creating city", error))
        );
    }


    private void validateCity(City city) {
        city.validateTitle();
    }

    private Mono<OutputCity> saveCity(City city) {
        return cityRepository.save(city.getTitle());
    }

    public record Output(Mono<OutputCity> result) {}
}
