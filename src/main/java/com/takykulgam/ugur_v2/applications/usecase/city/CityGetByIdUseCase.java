package com.takykulgam.ugur_v2.applications.usecase.city;

import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.CityRepository;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputCity;
import reactor.core.publisher.Mono;

public class CityGetByIdUseCase implements GenericUseCase<Mono<Long>, CityGetByIdUseCase.Output> {

    private final CityRepository cityRepository;

    public CityGetByIdUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<Long> request) {
        return new Output(request.flatMap(cityRepository::findById));
    }

    public record Output(Mono<OutputCity> result) {}
}
