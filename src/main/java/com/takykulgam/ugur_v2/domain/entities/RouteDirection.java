package com.takykulgam.ugur_v2.domain.entities;

import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.values.Point;

import java.util.List;

public record RouteDirection(String direction, List<Point> path) implements Domain {

    public void validationDirection() {
        if (direction == null || direction.isBlank()) throw new CoreException("Направление маршрута нулевое или пустое");
    }

    public void validationPath() {
        if (path == null || path.isEmpty()) throw new CoreException("Путь маршрута нулевой или пустой");
    }

}