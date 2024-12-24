package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.applications.iteractor.stop.CreateStopUseCase;
import com.takykulgam.ugur_v2.applications.iteractor.stop.RetrieveAllStopCase;
import com.takykulgam.ugur_v2.applications.iteractor.stop.StopDeleteUseCase;
import com.takykulgam.ugur_v2.applications.iteractor.stop.UpdateStopUseCase;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.core.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.interfaces.dto.stop.CreateStop;
import com.takykulgam.ugur_v2.interfaces.dto.stop.UpdateStop;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/stops")
public class StopController {

    private final CreateStopUseCase createStopUseCase;
    private final RetrieveAllStopCase retrieveAllStopCase;
    private final UpdateStopUseCase updateStopUseCase;
    private final StopDeleteUseCase stopDeleteUseCase;
    private final UseCaseExecutor useCaseExecutor;

    public StopController(CreateStopUseCase createStopUseCase,
                          RetrieveAllStopCase retrieveAllStopCase,
                          UpdateStopUseCase updateStopUseCase,
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
                Mono.just(new RetrieveAllStopCase.Input(page, size, sort, order)),
                RetrieveAllStopCase.Output::result
        );
    }

    @PostMapping
    public Mono<OutputStop> addStop(@RequestBody CreateStop createStop) {
        return useCaseExecutor.execute(
                createStopUseCase,
                Mono.just(createStop.toInput()),
                CreateStopUseCase.Output::result
        );
    }

    @PatchMapping("/{id}")
    public Mono<OutputStop> updateStop(@PathVariable Long id, @RequestBody UpdateStop updatedStop) {
        return useCaseExecutor.execute(
                updateStopUseCase,
                Mono.just(updatedStop.toInput(id)),
                UpdateStopUseCase.Output::result
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
