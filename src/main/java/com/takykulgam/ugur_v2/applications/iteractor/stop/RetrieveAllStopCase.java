package com.takykulgam.ugur_v2.applications.iteractor.stop;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.core.domain.gateways.StopRepository;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.utils.PaginationUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

public class RetrieveAllStopCase implements GenericUseCase<Mono<RetrieveAllStopCase.Input>, RetrieveAllStopCase.Output> {

    private final StopRepository stopRepository;


    public RetrieveAllStopCase(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request
                .flatMap(input -> stopRepository
                        .findAll()
                        .collectList()
                        .flatMap(fullList -> Flux
                                .fromIterable(fullList)
                                .transform(stops -> PaginationUtils.sort(
                                        stops,
                                        createComparator(input.sort, input.order))
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

    public record Input(int page, int size, String sort, String order) {}
    public record Output(Mono<PageResult<OutputStop>> result) {}
}
