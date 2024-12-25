package com.takykulgam.ugur_v2.applications.usecase.stop;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputPaginate;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.StopRepository;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.utils.PaginationUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

public class RetrieveAllStopCase implements GenericUseCase<Mono<InputPaginate>, RetrieveAllStopCase.Output> {

    private final StopRepository stopRepository;


    public RetrieveAllStopCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Override
    public Output execute(Mono<InputPaginate> request) {
        return new Output(request
                .flatMap(input -> stopRepository
                        .findAll()
                        .collectList()
                        .flatMap(fullList -> Flux
                                .fromIterable(fullList)
                                .transform(stops -> PaginationUtils.sort(
                                        stops,
                                        createComparator(input.sort(), input.order()))
                                )
                                .transform(stops -> PaginationUtils.paginate(
                                        stops,
                                        input.page(),
                                        input.size())
                                )
                                .collectList()
                                .map(stopList -> PaginationUtils.createPage(
                                        stopList,
                                        fullList.size(),
                                        input.page(),
                                        input.size())
                                )
                        )
                )
        );
    }



    private Comparator<OutputStop> createComparator(String sort, String order) {
        Comparator<OutputStop> comparator = switch (sort.toLowerCase()) {
            case "name" -> Comparator.comparing(OutputStop::name, String::compareToIgnoreCase);
            case "id" -> Comparator.comparingLong(OutputStop::id);
            default -> throw new CoreException("Unsupported sort field: %s".formatted(sort));
        };

        return "desc".equalsIgnoreCase(order) ? comparator.reversed() : comparator;
    }

    public record Output(Mono<PageResult<OutputStop>> result) {}
}
