package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffDeleteCase;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffUpdateCase;
import com.takykulgam.ugur_v2.interfaces.viewmodels.ListStaffViewModel;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffViewModel;
import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.RetrieveAllStaffCase;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffCreateCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/admin/staff")
public class StaffController {

    private final Presenter<OutputStaff, Mono<Response<StaffViewModel>>> presenter;
    private final Presenter<Flux<OutputStaff>, Mono<Response<ListStaffViewModel>>> presenterAll;
    private final StaffCreateCase staffCreateCase;
    private final RetrieveAllStaffCase retrieveAllStaffCase;
    private final StaffUpdateCase staffUpdateCase;
    private final StaffDeleteCase staffDelete;
    private final Presenter<String, Mono<Response<String>>> presenterDelete;

    @Autowired
    public StaffController(
            Presenter<OutputStaff, Mono<Response<StaffViewModel>>> presenter,
            Presenter<Flux<OutputStaff>, Mono<Response<ListStaffViewModel>>> presenterAll,
            StaffCreateCase staffCreateCase,
            RetrieveAllStaffCase retrieveAllStaffCase,
            StaffUpdateCase staffUpdateCase,
            StaffDeleteCase staffDelete,
            Presenter<String,Mono<Response<String>>> presenterDelete) {
        this.presenter = presenter;
        this.presenterAll = presenterAll;
        this.staffCreateCase = staffCreateCase;
        this.retrieveAllStaffCase = retrieveAllStaffCase;
        this.staffUpdateCase = staffUpdateCase;
        this.staffDelete = staffDelete;
        this.presenterDelete = presenterDelete;
    }

    @GetMapping
    public Mono<ResponseEntity<Response<ListStaffViewModel>>> getAllStaff() {
        return retrieveAllStaffCase.execute()
                .then(presenterAll.getResponse())
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<Response<StaffViewModel>>> createStaff(@RequestBody InputStaff inputStaff) {
        return staffCreateCase.execute(Mono.just(inputStaff))
                .then(presenter.getResponse())
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Response<StaffViewModel>>> updateStaff(@PathVariable Long id, @RequestBody InputStaff inputStaff) {
        inputStaff.setId(id);
        return staffUpdateCase.execute(Mono.just(inputStaff))
                .then(presenter.getResponse())
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response<String>>> deleteStaff(@PathVariable Long id) {
        return staffDelete.execute(id)
                .then(presenterDelete.getResponse())
                .map(ResponseEntity::ok);


    }
}
