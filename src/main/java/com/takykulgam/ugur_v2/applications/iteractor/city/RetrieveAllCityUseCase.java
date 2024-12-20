package com.takykulgam.ugur_v2.applications.iteractor.city;

import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputCity;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.gateways.CityRepository;
import com.takykulgam.ugur_v2.utils.PaginationUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

public class RetrieveAllCityUseCase implements GenericUseCase<Mono<RetrieveAllCityUseCase.Input>, RetrieveAllCityUseCase.Output> {

    private final CityRepository cityRepository;

    public RetrieveAllCityUseCase(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(
                request.flatMap(input -> cityRepository
                        .findAll()
                        .collectList()
                        .flatMap(fullList -> Flux
                                .fromIterable(fullList)
                                .transform(cities -> PaginationUtils.sort(cities, createComparator(
                                    input.sort(),
                                    input.order())))
                                .transform(cities -> PaginationUtils.paginate(cities, input.page(), input.size()))
                                .collectList()
                                .map(cityList -> createPage(cityList, fullList.size(),input.page(),input.size())))
                )
        );
    }

    private PageResult<OutputCity> createPage(List<OutputCity> cities, int fullListSize, int page, int size) {
        return new PageResult<>(cities, PaginationUtils.isLastPage(fullListSize, page,size));
    }

    private Comparator<OutputCity> createComparator(String sort, String order) {
        Comparator<OutputCity> comparator = switch (sort.toLowerCase()) {
            case "title" -> Comparator.comparing(OutputCity::title, String::compareToIgnoreCase);
            case "id" -> Comparator.comparingLong(OutputCity::id);
            default -> throw new IllegalArgumentException("Unsupported sort field: " + sort);
        };

        return "desc".equalsIgnoreCase(order) ? comparator.reversed() : comparator;
    }


    public record Input(int page, int size, String sort, String order) {}
    public record Output(Mono<PageResult<OutputCity>> result) {}
}
