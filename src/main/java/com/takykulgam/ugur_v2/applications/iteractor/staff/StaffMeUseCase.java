package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.core.domain.entities.Staff;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.iteractor.image.SaveImageService;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.utils.ImagePathUtils;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class StaffMeUseCase implements GenericUseCase<Mono<StaffMeUseCase.Input>, StaffMeUseCase.Output> {

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
    public Output execute(Mono<Input> request) {
        return new Output(request
                    .flatMap(this::processRequest)
                    .onErrorMap(error -> new CoreException(
                        String.format("Error processing staff update: %s", error.getMessage())))
        );
    }

    private Mono<OutputStaff> processRequest(Input input) {
        return staffRepository.findByIdGetPassword(input.id)
                .flatMap(storedPassword -> verifyPassword(input, storedPassword))
                .map(Input::toStaff)
                .doOnNext(this::validateStaff)
                .flatMap(staff -> {
                    if (isAvatarValid(staff.getAvatar()))
                        return saveImage(staff).flatMap(imagePath -> updateStaff(input, imagePath));
                    else return updateStaff(input, null);
                });
    }

    private Mono<Input> verifyPassword(Input input, String hashPassword) {
        Staff.createPassword(hashPassword).verifyPassword(input.currentPassword, customerPasswordEncoder);
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

    private Mono<OutputStaff> updateStaff(Input input, String avatarPath) {
        return staffRepository.updateMe(input.name, avatarPath, input.password);
    }

    public record Input(long id, String name, String currentPassword, String password, String avatar) {
        public Staff toStaff() {
            return new Staff(name, password, avatar, true);
        }
    }

    public record Output(Mono<OutputStaff> result) {}
}
