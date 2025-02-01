package com.takykulgam.ugur_v2.applications.usecase.staff;

import com.takykulgam.ugur_v2.applications.boundaries.input.staff.InputMe;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.applications.usecase.image.SaveImageService;
import com.takykulgam.ugur_v2.domain.entities.Staff;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.utils.ImagePathUtils;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class StaffMeUseCase implements GenericUseCase<Mono<InputMe>, StaffMeUseCase.Output> {

    private final StaffRepository staffRepository;
    private final SaveImageService saveImageService;
    private final CustomerPasswordEncoder customerPasswordEncoder;

    public StaffMeUseCase(StaffRepository staffRepository,
                          SaveImageService saveImageService,
                          CustomerPasswordEncoder customerPasswordEncoder) {
        this.staffRepository = staffRepository;
        this.saveImageService = saveImageService;
        this.customerPasswordEncoder = customerPasswordEncoder;
    }

    @Override
    public Output execute(Mono<InputMe> request) {
        return new Output(request
                    .flatMap(this::processRequest)
                    .onErrorMap(error -> new CoreException(
                        String.format("Error processing staff update: %s", error.getMessage())))
        );
    }

    private Mono<OutputStaff> processRequest(InputMe input) {
        return staffRepository.findByIdGetPassword(input.id())
                .flatMap(storedPassword -> verifyPassword(input, storedPassword))
                .map(InputMe::toEntity)
                .doOnNext(this::validateStaff)
                .flatMap(staff -> {
                    if (isAvatarValid(staff.getAvatar()))
                        return saveImage(staff).flatMap(imagePath -> updateStaff(staff, imagePath));
                    else return updateStaff(staff, null);
                });
    }

    private Mono<InputMe> verifyPassword(InputMe input, String hashPassword) {
        Staff.createPassword(hashPassword).verifyPassword(input.currentPassword(), customerPasswordEncoder);
        return Mono.just(input);
    }

    private void validateStaff(Staff staff) {
        staff.validateName();
        staff.validatePassword();
        if(isAvatarValid(staff.getAvatar())) staff.validateAvatar();
    }

    private boolean isAvatarValid(String avatar) {
        return Objects.nonNull(avatar) && avatar.matches("^[A-Za-z0-9+/=]+$");
    }

    private Mono<String> saveImage(Staff staff) {
        return saveImageService
                .execute(Mono.just(new SaveImageService.InputBase64(staff.getAvatar(), ImagePathUtils.AVATAR_PATH)))
                .path();
    }

    private Mono<OutputStaff> updateStaff(Staff input, String avatarPath) {
        return staffRepository.updateMe(input, avatarPath);
    }



    public record Output(Mono<OutputStaff> result) {}
}
