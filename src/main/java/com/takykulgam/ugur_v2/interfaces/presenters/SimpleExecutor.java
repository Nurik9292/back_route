package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.applications.boundaries.output.UseCaseExecutor;

import java.util.function.Function;

public class SimpleExecutor implements UseCaseExecutor {

    @Override
    public <F, T, RS> RS execute(GenericUseCase<F, T> executor, F input, Function<T, RS> outputMapper) {
        return outputMapper.apply(executor.execute(input));
    }
}
