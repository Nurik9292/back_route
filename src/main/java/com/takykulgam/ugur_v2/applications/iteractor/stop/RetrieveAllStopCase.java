package com.takykulgam.ugur_v2.applications.iteractor.stop;

import com.takykulgam.ugur_v2.core.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.StopRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RetrieveAllStopCase implements GenericUseCase<Mono<RetrieveAllStopCase.Input>, RetrieveAllStopCase.Output> {

    private final StopRepository stopRepository;


    public RetrieveAllStopCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(stopRepository.findAll());
    }

    public record Input(int page, int size, String sort, String order) {}
    public record Output(Flux<OutputStop> result) {}
}
