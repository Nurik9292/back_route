package com.takykulgam.ugur_v2.domain.entities;

import com.takykulgam.ugur_v2.domain.exceptions.CoreException;

public record Route(String name, RouteDirection routeDirection) {

    public void validationName() {
        if (name == null || name.isBlank()) throw new CoreException("Имя маршрута нулевое или пустое");
        if (name.length() < 3) throw new CoreException("Название маршрута слишком короткое");
        if (name.length() > 150) throw new CoreException("Название маршрута слишком длинное");
    }


    @Override
    public String toString() {
        return "Route{name='%s', routeDirection=%s}".formatted(name, routeDirection);
    }
}
