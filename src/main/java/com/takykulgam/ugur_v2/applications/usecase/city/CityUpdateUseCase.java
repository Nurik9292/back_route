package com.takykulgam.ugur_v2.applications.usecase.city;

import com.takykulgam.ugur_v2.applications.boundaries.input.stop.InputCityUpdate;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.entities.City;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.CityRepository;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputCity;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class CityUpdateUseCase implements GenericUseCase<Mono<InputCityUpdate>, CityUpdateUseCase.Output> {

    private final CityRepository cityRepository;
    private final CityGetByIdUseCase getCityByIdUseCase;

    public CityUpdateUseCase(CityRepository cityRepository, CityGetByIdUseCase getCityByIdUseCase) {
        this.cityRepository = cityRepository;
        this.getCityByIdUseCase = getCityByIdUseCase;
    }


    @Override
    public Output execute(Mono<InputCityUpdate> request) {
        return new Output(
                request.flatMap(this::getExistingCity)
                        .doOnNext(this::validateCity)
                        .flatMap(this::updateCity)
                        .doOnSuccess(result -> log.info("City successfully updated: {}", result))
                        .doOnError(error -> log.error("Error updating city", error))
        );
    }

    private Mono<City> getExistingCity(InputCityUpdate input) {
        return getCityByIdUseCase.execute(Mono.just(input.id()))
                .result()
                .switchIfEmpty(Mono.error(new CoreException("City not found")))
                .map(existingCity -> new City(existingCity.id(), input.name()));
    }


    private void validateCity(City city) {
        city.validateTitle();
    }

    private Mono<OutputCity> updateCity(City city) {
        return cityRepository.update(city.getId(), city.getTitle());
    }


    public record Output(Mono<OutputCity> result) {}
}
