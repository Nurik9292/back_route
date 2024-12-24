package com.takykulgam.ugur_v2.applications.iteractor.stop;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.core.domain.entities.Stop;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.core.domain.gateways.StopRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class UpdateStopUseCase implements GenericUseCase<Mono<UpdateStopUseCase.Input>, UpdateStopUseCase.Output> {

    private final StopRepository stopRepository;
    private final GetStopByIdUseCase getStopByIdUseCase;

    public UpdateStopUseCase(StopRepository stopRepository, GetStopByIdUseCase getStopByIdUseCase) {
        this.stopRepository = stopRepository;
        this.getStopByIdUseCase = getStopByIdUseCase;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request.flatMap(this::getExistingStop)
                .doOnNext(this::validateStop)
                .flatMap(this::updateStop)
                .doOnSuccess(result -> log.info("Stop successfully updated: {}", result))
                .doOnError(error -> log.error("Error updating stop", error))
                .onErrorResume(e ->  Mono.error(new CoreException("Ошибка сохранения остановки: %s".formatted(e.getMessage()))))
        );
    }


    private Mono<Stop> getExistingStop(Input input) {
        return getStopByIdUseCase.execute(Mono.just(new GetStopByIdUseCase.Input(input.id())))
                .result()
                .switchIfEmpty(Mono.error(new CoreException("Остановка не найдена")))
                .thenReturn(toEntity(input));
    }


    private void validateStop(Stop stop) {
        stop.validateCity();
        stop.validateLocation();
        stop.validateTitle();
    }

    private Mono<OutputStop> updateStop(Stop stop) {
        return stopRepository.update(
                stop.getId(),
                stop.getTitle(),
                stop.getLocation().x(),
                stop.getLocation().y(),
                stop.getCity().id());
    }

    private Stop toEntity(Input input) {
        return new Stop(
                input.id(),
                input.name(),
                new Stop.Point(input.location.x(), input.location().y()),
                new Stop.City(input.city().id()));
    }


    public record Point(double x, double y) {}

    public record City(long id){}

    public record Input(long id, String name, Point location, City city) {

    }


    public record Output(Mono<OutputStop> result) {}
}
