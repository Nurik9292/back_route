package com.takykulgam.ugur_v2.applications.iteractor.city;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputCity;
import com.takykulgam.ugur_v2.core.domain.gateways.CityRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FetchAllCityUseCase implements GenericUseCase<Mono<Void>, FetchAllCityUseCase.Output> {

    private final CityRepository cityRepository;

    public FetchAllCityUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<Void> request) {
        return new Output(cityRepository.findAll());
    }


    public record Output(Flux<OutputCity> result){}
}
