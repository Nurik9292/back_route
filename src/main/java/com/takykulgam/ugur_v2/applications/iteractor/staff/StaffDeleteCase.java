package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.applications.iteractor.image.DeleteImageService;
import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.interfaces.dto.staff.OutputStaff;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StaffDeleteCase implements GenericUseCase<Mono<StaffDeleteCase.Input>, StaffDeleteCase.Output> {

    private static final Path PATH_AVATAR =  Path.of("avatar");

    private final StaffRepository staffRepository;
    private final DeleteImageService deleteImageService;
    private final GetStaffByIdUseCase getStaffByIdUseCase;

    public StaffDeleteCase(StaffRepository staffRepository,
                           DeleteImageService deleteImageService,
                           GetStaffByIdUseCase getStaffByIdUseCase) {

        this.staffRepository = staffRepository;
        this.deleteImageService = deleteImageService;
        this.getStaffByIdUseCase = getStaffByIdUseCase;
    }


    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request
                .flatMap(this::findStaff)
                .flatMap(this::deleteAvatar)
                .flatMap(this::deleteStaff)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete staff", e))));
    }

    private Mono<OutputStaff> findStaff(Input input) {
        return getStaffByIdUseCase
                .execute(Mono.just(new GetStaffByIdUseCase.Input(input.id())))
                .result();
    }

    private Mono<OutputStaff> deleteAvatar(OutputStaff outputStaff) {
        if (outputStaff.getAvatar() != null) {
            return deleteImageService
                    .execute(Mono.just(new DeleteImageService.Input(outputStaff.getAvatar(),PATH_AVATAR)))
                    .result()
                    .thenReturn(outputStaff);
        }
        return Mono.just(outputStaff);
    }

    private Mono<String> deleteStaff(OutputStaff outputStaff) {
        return staffRepository.delete(outputStaff.getId())
                .thenReturn("Staff deleted successfully");
    }

    public record Input(long id) {}

    public record Output(Mono<String> message) {}
}
