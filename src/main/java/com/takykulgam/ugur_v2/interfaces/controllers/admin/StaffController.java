package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.interfaces.dto.staff.CreateStaff;
import com.takykulgam.ugur_v2.interfaces.dto.staff.MeStaff;
import com.takykulgam.ugur_v2.interfaces.dto.staff.OutputStaff;
import com.takykulgam.ugur_v2.interfaces.dto.staff.UpdateStaff;
import com.takykulgam.ugur_v2.applications.iteractor.staff.*;
import com.takykulgam.ugur_v2.core.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.infrastructure.storage.FileSystemStorage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/admin/staff")
public class StaffController {

    private static final String PATH_AVATAR = "avatar/";

    private final UseCaseExecutor useCaseExecutor;
    private final RetrieveAllStaffCase retrieveAllStaffCase;
    private final StaffCreateCase staffCreateCase;
    private final StaffUpdateCase staffUpdateCase;
    private final StaffDeleteCase staffDeleteCase;
    private final StaffMeUseCase staffMeUseCase;
    private final FileSystemStorage fileSystemStorage;

    @Autowired
    public StaffController(
            UseCaseExecutor useCaseExecutor,
            RetrieveAllStaffCase retrieveAllStaffCase,
            StaffCreateCase staffCreateCase,
            StaffUpdateCase staffUpdateCase,
            StaffDeleteCase staffDeleteCase,
            StaffMeUseCase staffMeUseCase,
            FileSystemStorage fileSystemStorage) {
        this.useCaseExecutor = useCaseExecutor;
        this.retrieveAllStaffCase = retrieveAllStaffCase;
        this.staffCreateCase = staffCreateCase;
        this.staffUpdateCase = staffUpdateCase;
        this.staffDeleteCase = staffDeleteCase;
        this.staffMeUseCase = staffMeUseCase;
        this.fileSystemStorage = fileSystemStorage;
    }

    @GetMapping
    public Flux<OutputStaff>  getAllStaff() {
        return useCaseExecutor.execute(
                retrieveAllStaffCase,
                Mono.empty(),
                RetrieveAllStaffCase.Output::result);
    }

    @PostMapping
    public Mono<OutputStaff> createStaff(@RequestBody CreateStaff createStaff) {
        System.out.println(createStaff);
        return useCaseExecutor.execute(
                staffCreateCase,
                Mono.just(createStaff).map(CreateStaff::toInput),
                StaffCreateCase.Output::result);
    }

    @PatchMapping("/{id}")
    public Mono<OutputStaff> updateStaff(@PathVariable Long id, @RequestBody UpdateStaff updateStaff) {
        return useCaseExecutor.execute(
                staffUpdateCase,
                Mono.just(updateStaff).map(staff -> staff.toInput(id)),
                StaffUpdateCase.Output::result);
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteStaff(@PathVariable Long id) {
        return useCaseExecutor.execute(
                staffDeleteCase,
                Mono.empty(),
                StaffDeleteCase.Output::message
        );
    }

    @PatchMapping("/me")
    public Mono<OutputStaff> updateMe(@RequestBody MeStaff meStaff) {
        return useCaseExecutor.execute(
                staffMeUseCase,
                Mono.just(meStaff).map(MeStaff::toInput),
                StaffMeUseCase.Output::result
                );
    }

    @GetMapping(value = "/avatar/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<Resource>> getStaffAvatar(@PathVariable String imageName) {

        return fileSystemStorage
                .loadAsResource(PATH_AVATAR + imageName)
                .map(path -> ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(path))
                .onErrorResume(e -> {
                    log.error("Ошибка при загрузке изображения: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.notFound().build());
                });
    }

}
