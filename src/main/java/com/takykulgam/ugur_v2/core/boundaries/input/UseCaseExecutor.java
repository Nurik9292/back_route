package com.takykulgam.ugur_v2.core.boundaries.input;

import java.util.function.Function;

public interface UseCaseExecutor {
    <F,T,RS> RS execute(GenericUseCase<F,T> executor, F input, Function<T,RS> outputMapper);
}
