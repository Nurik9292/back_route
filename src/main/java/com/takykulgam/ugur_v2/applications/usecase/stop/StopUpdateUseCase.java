package com.takykulgam.ugur_v2.applications.usecase.stop;

import com.takykulgam.ugur_v2.applications.boundaries.input.stop.InputStopUpdate;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.domain.entities.Stop;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.StopRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class StopUpdateUseCase implements GenericUseCase<Mono<InputStopUpdate>, StopUpdateUseCase.Output> {

    private final StopRepository stopRepository;
    private final StopGetByIdUseCase getStopByIdUseCase;

    public StopUpdateUseCase(StopRepository stopRepository, StopGetByIdUseCase getStopByIdUseCase) {
        this.stopRepository = stopRepository;
        this.getStopByIdUseCase = getStopByIdUseCase;
    }

    @Override
    public Output execute(Mono<InputStopUpdate> request) {
        return new Output(request.flatMap(this::getExistingStop)
                .doOnNext(this::validateStop)
                .flatMap(this::updateStop)
                .doOnSuccess(result -> log.info("Stop successfully updated: {}", result))
                .doOnError(error -> log.error("Error updating stop", error))
                .onErrorResume(e ->  Mono.error(new CoreException("Ошибка сохранения остановки: %s".formatted(e.getMessage()))))
        );
    }


    private Mono<Stop> getExistingStop(InputStopUpdate input) {
        return getStopByIdUseCase.execute(Mono.just(input.id()))
                .result()
                .switchIfEmpty(Mono.error(new CoreException("Остановка не найдена")))
                .then(input.toEntity());
    }


    private void validateStop(Stop stop) {
        stop.validateCity();
        stop.validateLocation();
        stop.validateTitle();
    }

    private Mono<OutputStop> updateStop(Stop stop) {
        return stopRepository.update(stop.getId(), stop);
    }

    public record Output(Mono<OutputStop> result) {}
}
