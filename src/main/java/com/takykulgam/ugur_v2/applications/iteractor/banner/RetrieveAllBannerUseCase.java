package com.takykulgam.ugur_v2.applications.iteractor.banner;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.core.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.interfaces.dto.banner.OutputBanner;
import com.takykulgam.ugur_v2.utils.PaginationUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;


public class RetrieveAllBannerUseCase implements
        GenericUseCase<Mono<RetrieveAllBannerUseCase.Input>, RetrieveAllBannerUseCase.Output> {

    private final BannerRepository bannerRepository;

    public RetrieveAllBannerUseCase(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
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
                                .map(banerList -> createPage(banerList, fullList.size(), input.page, input.size))
                        )

                )
        );
    }

    private PageResult<OutputBanner> createPage(List<OutputBanner> banners, int fullListSize, int page, int size) {
        return new PageResult<>(banners, PaginationUtils.isLastPage(fullListSize, page,size));
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
