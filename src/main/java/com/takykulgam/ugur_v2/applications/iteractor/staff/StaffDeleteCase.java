package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import reactor.core.publisher.Mono;

public class StaffDeleteCase implements GenericUseCase<Mono<StaffDeleteCase.Input>, StaffDeleteCase.Output> {

    private final StaffRepository staffRepository;

    public StaffDeleteCase( StaffRepository staffRepository) {

        this.staffRepository = staffRepository;
    }


    @Override
    public Output execute(Mono<Input> request) {
        request.map(req -> staffRepository.delete(req.id)).subscribe();
        return new Output(Mono.just("Staff delete"));
    }

    public record Input(long id) {}

    public record Output(Mono<String> message) {}
}
