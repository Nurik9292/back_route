package com.takykulgam.ugur_v2.applications.processors;

public interface EntityProcessor<T> {
    void preprocessBeforeSave(T entity);
    void preprocessBeforeUpdate(T entity);
}
