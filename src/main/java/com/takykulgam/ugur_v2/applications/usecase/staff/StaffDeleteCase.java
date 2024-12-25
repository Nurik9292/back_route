package com.takykulgam.ugur_v2.applications.usecase.staff;

import com.takykulgam.ugur_v2.applications.usecase.image.DeleteImageService;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

@Log4j2
public class StaffDeleteCase implements GenericUseCase<Mono<Long>, StaffDeleteCase.Output> {

    private static final Path PATH_AVATAR =  Path.of("avatar");

    private final StaffRepository staffRepository;
    private final DeleteImageService deleteImageService;
    private final StaffGetByIdUseCase getStaffByIdUseCase;

    public StaffDeleteCase(StaffRepository staffRepository,
                           DeleteImageService deleteImageService,
                           StaffGetByIdUseCase getStaffByIdUseCase) {

        this.staffRepository = staffRepository;
        this.deleteImageService = deleteImageService;
        this.getStaffByIdUseCase = getStaffByIdUseCase;
    }


    @Override
    public Output execute(Mono<Long> request) {
        return new Output(request
                .flatMap(this::findStaff)
                .flatMap(this::deleteAvatar)
                .flatMap(this::deleteStaff)
                .doOnSuccess(result -> log.info("Staff successfully deleted: {}", result))
                .doOnError(error -> log.error("Error deleting staff", error))
                .onErrorResume(e -> Mono.error(new CoreException("Failed to delete staff%s".formatted(e.getMessage())))));
    }

    private Mono<OutputStaff> findStaff(Long input) {
        return getStaffByIdUseCase
                .execute(Mono.just(input))
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
