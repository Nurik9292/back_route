package com.takykulgam.ugur_v2.applications.usecase.stop;

import com.takykulgam.ugur_v2.applications.boundaries.input.stop.InputStopCreate;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.domain.entities.Stop;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.StopRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class StopCreateUseCase implements GenericUseCase<Mono<InputStopCreate>, StopCreateUseCase.Output> {

    private final StopRepository stopRepository;

    public StopCreateUseCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }


    public Output execute(Mono<InputStopCreate> request) {
        return new Output(
                request.flatMap(input -> input.toEntity()
                        .doOnNext(Stop::validateTitle)
                        .doOnNext(Stop::validateLocation)
                        .doOnNext(Stop::validateCity)
                        .flatMap(stopRepository::save)
                        .doOnSuccess(e -> log.info("Stop successfully saved: {}", e))
                        .doOnError(e -> log.error("Error saving stop: ", e))
                        .onErrorResume(e ->  Mono.error(new CoreException("Ошибка сохранения остановки: %s".formatted(e.getMessage()))))
                )
        );
    }

    public record Output(Mono<OutputStop> result) {}
}
