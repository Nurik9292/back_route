package com.takykulgam.ugur_v2.applications.iteractor.stop;

import com.takykulgam.ugur_v2.core.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.entities.Stop;
import com.takykulgam.ugur_v2.core.domain.gateways.StopRepository;
import reactor.core.publisher.Mono;

public class CreateStopUseCase implements GenericUseCase<Mono<CreateStopUseCase.Input>, CreateStopUseCase.Output> {

    private final StopRepository stopRepository;

    public CreateStopUseCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }


    @Override
    public Output execute(Mono<Input> request) {
        Mono<OutputStop> outputStop = request
                .flatMap(Input::toEntity)
                .doOnNext(Stop::validateTitle)
                .doOnNext(Stop::validateLocation)
                .doOnNext(Stop::validateCity)
                .flatMap(stop -> stopRepository.save(
                        stop.getTitle(),
                        stop.getLocation().x(),
                        stop.getLocation().y(),
                        stop.getCity().id()));
        return new Output(outputStop);
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
