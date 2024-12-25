package com.takykulgam.ugur_v2.applications.usecase.banner;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputPaginate;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputBanner;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.utils.PaginationUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;


public class RetrieveAllBannerUseCase implements GenericUseCase<Mono<InputPaginate>, RetrieveAllBannerUseCase.Output> {

    private final BannerRepository bannerRepository;

    public RetrieveAllBannerUseCase(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Output execute(Mono<InputPaginate> request) {
        return new Output(request
                .flatMap(input -> bannerRepository
                        .findAll()
                        .collectList()
                        .flatMap(fullList -> Flux
                                .fromIterable(fullList)
                                .transform(banners -> PaginationUtils.sort(banners, createComparator(
                                        input.sort(),
                                        input.order())))
                                .transform(banners -> PaginationUtils.paginate(banners, input.page(), input.size()))
                                .collectList()
                                .map(banerList ->  PaginationUtils.createPage(banerList, fullList.size(), input.page(), input.size()))
                        )

                )
        );
    }



    private Comparator<OutputBanner> createComparator(String sort, String order) {
        Comparator<OutputBanner> comparator = switch (sort.toLowerCase()) {
            case "id" -> Comparator.comparingLong(OutputBanner::id);
            default -> throw new CoreException("Unsupported sort fields");
        };

        return "desc".equalsIgnoreCase(order) ? comparator.reversed() : comparator;
    }


    public record Input(int page, int size, String sort, String order) {}

    public record Output(Mono<PageResult<OutputBanner>> result) {}
}
