package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputPaginate;
import com.takykulgam.ugur_v2.applications.usecase.stop.StopCreateUseCase;
import com.takykulgam.ugur_v2.applications.usecase.stop.RetrieveAllStopCase;
import com.takykulgam.ugur_v2.applications.usecase.stop.StopDeleteUseCase;
import com.takykulgam.ugur_v2.applications.usecase.stop.StopUpdateUseCase;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.applications.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.interfaces.dto.stop.StopCreate;
import com.takykulgam.ugur_v2.interfaces.dto.stop.StopUpdate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/stops")
public class StopController {

    private final StopCreateUseCase createStopUseCase;
    private final RetrieveAllStopCase retrieveAllStopCase;
    private final StopUpdateUseCase updateStopUseCase;
    private final StopDeleteUseCase stopDeleteUseCase;
    private final UseCaseExecutor useCaseExecutor;

    public StopController(StopCreateUseCase createStopUseCase,
                          RetrieveAllStopCase retrieveAllStopCase,
                          StopUpdateUseCase updateStopUseCase,
                          StopDeleteUseCase stopDeleteUseCase,
                          UseCaseExecutor useCaseExecutor) {
        this.createStopUseCase = createStopUseCase;
        this.retrieveAllStopCase = retrieveAllStopCase;
        this.updateStopUseCase = updateStopUseCase;
        this.stopDeleteUseCase = stopDeleteUseCase;
        this.useCaseExecutor = useCaseExecutor;
    }

    @GetMapping
    public Mono<PageResult<OutputStop>> findAllStops(
            @RequestParam(name = "order") String order,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return useCaseExecutor.execute(
                retrieveAllStopCase,
                Mono.just(new InputPaginate(page, size, sort, order)),
                RetrieveAllStopCase.Output::result
        );
    }

    @PostMapping
    public Mono<OutputStop> addStop(@RequestBody StopCreate createStop) {
        return useCaseExecutor.execute(
                createStopUseCase,
                createStop.toInput(),
                StopCreateUseCase.Output::result
        );
    }

    @PatchMapping("/{id}")
    public Mono<OutputStop> updateStop(@PathVariable Long id, @RequestBody StopUpdate updatedStop) {
        return useCaseExecutor.execute(
                updateStopUseCase,
                updatedStop.toInput(id),
                StopUpdateUseCase.Output::result
        );
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteStop(@PathVariable Long id) {
        return useCaseExecutor.execute(
                stopDeleteUseCase,
                Mono.just(new StopDeleteUseCase.Input(id)),
                StopDeleteUseCase.Output::message
        );
    }
}
