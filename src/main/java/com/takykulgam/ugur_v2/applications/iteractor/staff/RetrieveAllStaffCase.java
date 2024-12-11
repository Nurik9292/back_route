package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.interfaces.dto.staff.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RetrieveAllStaffCase
        implements GenericUseCase<Mono<Void>, RetrieveAllStaffCase.Output> {

    private final StaffRepository staffRepository;

    public RetrieveAllStaffCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    @Override
    public Output execute(Mono<Void> request) {
        return new Output(staffRepository.findAll());
    }


    public record Output(Flux<OutputStaff> result) {}
}
