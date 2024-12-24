package com.takykulgam.ugur_v2.applications.iteractor.city;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.entities.City;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.core.domain.gateways.CityRepository;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputCity;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class CityUpdateUseCase implements GenericUseCase<Mono<CityUpdateUseCase.Input>, CityUpdateUseCase.Output> {

    private final CityRepository cityRepository;
    private final GetCityByIdUseCase getCityByIdUseCase;

    public CityUpdateUseCase(CityRepository cityRepository, GetCityByIdUseCase getCityByIdUseCase) {
        this.cityRepository = cityRepository;
        this.getCityByIdUseCase = getCityByIdUseCase;
    }


    @Override
    public Output execute(Mono<Input> request) {
        return new Output(
                request.flatMap(this::getExistingCity)
                        .doOnNext(this::validateCity)
                        .flatMap(this::updateCity)
                        .doOnSuccess(result -> log.info("City successfully updated: {}", result))
                        .doOnError(error -> log.error("Error updating city", error))
        );
    }

    private Mono<City> getExistingCity(Input input) {
        return getCityByIdUseCase.execute(Mono.just(new GetCityByIdUseCase.Input(input.id())))
                .result()
                .switchIfEmpty(Mono.error(new CoreException("City not found")))
                .map(existingCity -> new City(existingCity.id(), input.title()));
    }


    private void validateCity(City city) {
        city.validateTitle();
    }

    private Mono<OutputCity> updateCity(City city) {
        return cityRepository.update(city.getId(), city.getTitle());
    }


    public record Input(long id, String title) {
    }

    public record Output(Mono<OutputCity> result) {}
}
