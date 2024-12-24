package com.takykulgam.ugur_v2.applications.iteractor.stop;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.core.domain.entities.Stop;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.core.domain.gateways.StopRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class CreateStopUseCase implements GenericUseCase<Mono<CreateStopUseCase.Input>, CreateStopUseCase.Output> {

    private final StopRepository stopRepository;

    public CreateStopUseCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }


    public Output execute(Mono<Input> request) {
        System.out.println("test");
        return new Output(
                request.flatMap(input -> input.toEntity()
                        .doOnNext(Stop::validateTitle)
                        .doOnNext(Stop::validateLocation)
                        .doOnNext(Stop::validateCity)
                        .flatMap(this::saveStop)
                        .doOnError(e -> log.error("Ошибка при сохранении в репозиторий: ", e))
                        .onErrorResume(e ->  Mono.error(new CoreException("Ошибка сохранения остановки: %s".formatted(e.getMessage()))))
                )
        );
    }

    private Mono<OutputStop> saveStop(Stop stop) {
        return stopRepository.save(
                stop.getTitle(),
                stop.getLocation().x(),
                stop.getLocation().y(),
                stop.getCity().id()
        );
    }

    public record Point(double x, double y) {}

    public record City(long id){}

    public record Input(String title, Point location, City city) {
        public Mono<Stop> toEntity() {
            return Mono.just(new Stop(title, new Stop.Point(location.x, location.y), new Stop.City(city.id)));
        }
    }

    public record Output(Mono<OutputStop> result) {}
}
