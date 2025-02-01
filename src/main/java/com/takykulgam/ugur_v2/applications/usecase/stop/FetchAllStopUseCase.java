package com.takykulgam.ugur_v2.applications.usecase.stop;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStopForRoute;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.StopRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FetchAllStopUseCase implements GenericUseCase<Mono<Void>, FetchAllStopUseCase.Output> {

    private final StopRepository stopRepository;

    public FetchAllStopUseCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Override
    public Output execute(Mono<Void> request) {
        return new Output(stopRepository.fetchAll());
    }


    public record Output(Flux<OutputStopForRoute> result) {}
}
