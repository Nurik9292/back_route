package com.takykulgam.ugur_v2.utils;

import com.takykulgam.ugur_v2.core.boundaries.output.OutputStop;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;

public class PaginationUtils {


    public static <T> Flux<T> paginate(Flux<T> items, int page, int size) {
        int skip = (page - 1) * size;
        return items.skip(skip).take(size);
    }


    public static <T> Flux<T> sort(Flux<T> items, Comparator<T> comparator) {
        return items.collectSortedList(comparator)
                .flatMapMany(Flux::fromIterable);
    }


    public static boolean isLastPage(int totalElements, int page, int size) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return page >= totalPages;
    }

    public static <T> PageResult<T> createPage(List<T> stops, int fullListSize, int page, int size) {
        return new PageResult<>(stops, PaginationUtils.isLastPage(fullListSize, page,size));
    }
}
