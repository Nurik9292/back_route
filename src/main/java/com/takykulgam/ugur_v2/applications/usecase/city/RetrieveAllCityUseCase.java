package com.takykulgam.ugur_v2.applications.usecase.city;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputPaginate;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputCity;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.CityRepository;
import com.takykulgam.ugur_v2.utils.PaginationUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

public class RetrieveAllCityUseCase implements GenericUseCase<Mono<InputPaginate>, RetrieveAllCityUseCase.Output> {

    private final CityRepository cityRepository;

    public RetrieveAllCityUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<InputPaginate> request) {
        return new Output(request
                .flatMap(input -> cityRepository
                    .findAll()
                    .collectList()
                    .flatMap(fullList -> Flux
                        .fromIterable(fullList)
                        .transform(cities -> PaginationUtils.sort(
                                cities,
                                createComparator(input.sort(), input.order()))
                        )
                        .transform(cities -> PaginationUtils.paginate(
                                cities,
                                input.page(),
                                input.size())
                        )
                        .collectList()
                        .map(cityList -> PaginationUtils.createPage(
                                cityList,
                                fullList.size(),
                                input.page(),
                                input.size())
                        )
                    )
                )
        );
    }



    private Comparator<OutputCity> createComparator(String sort, String order) {
        Comparator<OutputCity> comparator = switch (sort.toLowerCase()) {
            case "title" -> Comparator.comparing(OutputCity::title, String::compareToIgnoreCase);
            case "id" -> Comparator.comparingLong(OutputCity::id);
            default -> throw new IllegalArgumentException("Unsupported sort field: %s".formatted(sort));
        };

        return "desc".equalsIgnoreCase(order) ? comparator.reversed() : comparator;
    }



    public record Output(Mono<PageResult<OutputCity>> result) {}
}
