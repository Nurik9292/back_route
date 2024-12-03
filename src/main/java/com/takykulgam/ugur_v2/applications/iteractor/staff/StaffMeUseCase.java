package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.applications.dto.OutputStaff;
import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.iteractor.image.SaveImageService;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import reactor.core.publisher.Mono;

public class StaffMeUseCase implements GenericUseCase<Mono<StaffMeUseCase.Input>, StaffMeUseCase.Output> {

    private final StaffRepository staffRepository;
    private final SaveImageService saveImageService;

    public StaffMeUseCase(StaffRepository staffRepository, SaveImageService saveImageService) {
        this.staffRepository = staffRepository;
        this.saveImageService = saveImageService;
    }

    @Override
    public Output execute(Mono<StaffMeUseCase.Input> request) {
        Mono<OutputStaff> outputStaff =  request.flatMap(req -> {
            return saveImageService.execute(Mono.just(new SaveImageService.Inout(req.avatar))).path()
                    .flatMap(avatar ->  staffRepository.updateMe(req.name, avatar, req.password));});

        return new StaffMeUseCase.Output(outputStaff);
    }

    public record Input(String name, String password, String avatar) {}

    public record Output(Mono<OutputStaff> result) {}
}
