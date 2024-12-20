package com.takykulgam.ugur_v2.applications.iteractor.city;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.CityRepository;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputCity;
import reactor.core.publisher.Mono;

public class GetCityByIdUseCase implements GenericUseCase<Mono<GetCityByIdUseCase.Input>, GetCityByIdUseCase.Output> {

    private final CityRepository cityRepository;

    public GetCityByIdUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(
                request
                        .map(Input::id)
                        .flatMap(cityRepository::findById));
    }

    public record Input(long id) {}

    public record Output(Mono<OutputCity> result) {}
}
