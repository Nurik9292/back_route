package com.takykulgam.ugur_v2.applications.usecase;

public interface GenericUseCase<F,T> {
    T execute(F request);
}
