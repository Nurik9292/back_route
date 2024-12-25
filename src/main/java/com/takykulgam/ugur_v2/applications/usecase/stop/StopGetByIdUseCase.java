package com.takykulgam.ugur_v2.applications.usecase.stop;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.StopRepository;
import reactor.core.publisher.Mono;

public class StopGetByIdUseCase implements GenericUseCase<Mono<Long>, StopGetByIdUseCase.Output> {

    private final StopRepository stopRepository;

    public StopGetByIdUseCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Override
    public Output execute(Mono<Long> request) {
        return new Output(request.flatMap(stopRepository::findById)
                .switchIfEmpty(Mono.error(new CoreException("Stop not found"))));
    }

    public record Output(Mono<OutputStop> result) {}
}
