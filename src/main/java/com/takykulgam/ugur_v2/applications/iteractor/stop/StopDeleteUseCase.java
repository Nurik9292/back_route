package com.takykulgam.ugur_v2.applications.iteractor.stop;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.StopRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class StopDeleteUseCase implements GenericUseCase<Mono<StopDeleteUseCase.Input>, StopDeleteUseCase.Output> {

    private final StopRepository stopRepository;

    public StopDeleteUseCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request
                .flatMap(this::deleteStopById)
                .doOnSuccess(result -> log.info("Stop successfully deleted: {}", result))
                .doOnError(error -> log.error("Error deleting stop", error))
        );
    }

    private Mono<String> deleteStopById(Input input) {
        return stopRepository.deleteById(input.id())
                .then(Mono.just("Stop deleted successfully"));
    }

    public record Input(long id) {}

    public record Output(Mono<String> message) {}
}
