package com.takykulgam.ugur_v2.applications.iteractor.stop;

import com.takykulgam.ugur_v2.interfaces.dto.stop.OutputStop;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.core.domain.gateways.StopRepository;
import reactor.core.publisher.Mono;

public class GetStopByIdUseCase implements GenericUseCase<Mono<GetStopByIdUseCase.Input>, GetStopByIdUseCase.Output> {

    private final StopRepository stopRepository;

    public GetStopByIdUseCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request.flatMap(input -> stopRepository.findById(input.id))
                .switchIfEmpty(Mono.error(new CoreException("Stop not found"))));
    }


    public record Input(long id) {}
    public record Output(Mono<OutputStop> result) {}
}
