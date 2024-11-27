package com.takykulgam.ugur_v2.core.boundaries.input;

public interface GenericUseCase<F,T> {
    T execute(F request);
}
