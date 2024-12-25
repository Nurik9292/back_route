package com.takykulgam.ugur_v2.applications.boundaries.output;

import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;

import java.util.function.Function;

public interface UseCaseExecutor {
    <F,T,RS> RS execute(GenericUseCase<F,T> executor, F input, Function<T,RS> outputMapper);


}
