package com.takykulgam.ugur_v2.core.boundaries.output;

public interface Presenter<I,O> {
    O getResponse();
    void present(boolean success, I item);
}
