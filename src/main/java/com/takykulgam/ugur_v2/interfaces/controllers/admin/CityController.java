package com.takykulgam.ugur_v2.interfaces.controllers.admin;


import com.takykulgam.ugur_v2.applications.iteractor.city.*;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.interfaces.dto.city.CreateCity;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputCity;
import com.takykulgam.ugur_v2.core.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.interfaces.dto.city.UpdateCity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/cities")
public class CityController {

    private final UseCaseExecutor useCaseExecutor;
    private final RetrieveAllCityUseCase retrieveAllCityUseCase;
    private final CityCreateUseCase cityCreateUseCase;
    private final CityUpdateUseCase cityUpdateUseCase;
    private final CityDeleteUseCase cityDeleteUseCase;
    private final FetchAllCityUseCase fetchAllCityUseCase;



    public CityController(UseCaseExecutor useCaseExecutor,
                          RetrieveAllCityUseCase retrieveAllCityUseCase,
                          CityCreateUseCase cityCreateUseCase,
                          CityUpdateUseCase cityUpdateUseCase,
                          CityDeleteUseCase cityDeleteUseCase,
                          FetchAllCityUseCase fetchAllCityUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.retrieveAllCityUseCase = retrieveAllCityUseCase;
        this.cityCreateUseCase = cityCreateUseCase;
        this.cityUpdateUseCase = cityUpdateUseCase;
        this.cityDeleteUseCase = cityDeleteUseCase;
        this.fetchAllCityUseCase = fetchAllCityUseCase;
    }

    @GetMapping("/all")
    public Flux<OutputCity> getAllCity() {
        return useCaseExecutor.execute(
                fetchAllCityUseCase,
                Mono.empty(),
                FetchAllCityUseCase.Output::result
        );
    }

    @GetMapping
    public Mono<PageResult<OutputCity>> getAllCities(
            @RequestParam(name = "order") String order,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
            ) {

        return useCaseExecutor.execute(
                retrieveAllCityUseCase,
                Mono.just(new RetrieveAllCityUseCase.Input(page, size, sort, order)),
                RetrieveAllCityUseCase.Output::result
        );
    }

    @PostMapping
    public Mono<OutputCity> addCity(@RequestBody CreateCity inputCity) {
        return useCaseExecutor.execute(
                cityCreateUseCase,
                Mono.just(new CityCreateUseCase.Input(inputCity.title())),
                CityCreateUseCase.Output::result
        );
    }

    @PatchMapping("/{id}")
    public Mono<OutputCity> updateCity(@RequestBody UpdateCity updateCity, @PathVariable long id) {
        return useCaseExecutor.execute(
                cityUpdateUseCase,
                Mono.just(updateCity).map(city -> city.toInput(id)),
                CityUpdateUseCase.Output::result
        );
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteCity(@PathVariable long id) {
        return useCaseExecutor.execute(
                cityDeleteUseCase,
                Mono.just(new CityDeleteUseCase.Input(id)),
                CityDeleteUseCase.Output::message
        );
    }
}
