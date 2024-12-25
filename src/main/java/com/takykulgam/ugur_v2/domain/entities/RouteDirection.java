package com.takykulgam.ugur_v2.domain.entities;

import com.takykulgam.ugur_v2.domain.exceptions.CoreException;

import java.util.List;

public class RouteDirection {
    String direction;
    List<Point> path;

    public RouteDirection(String direction, List<Point> path) {
        this.direction = direction;
        this.path = path;
    }

    public void validationDirection() {
        if (direction == null || direction.isBlank()) throw new CoreException("Направление маршрута нулевое или пустое");
    }

    public void validationPath() {
        if (path == null || path.isEmpty()) throw new CoreException("Путь маршрута нулевой или пустой");
    }


    public record Point(double x, double y) {

        public void validation() {
            if (x > 35.00 && x < 42.00)
                throw new CoreException("Широта должна быть в диапазоне от 35.00 до 42.00");

            if (y > 52.00 && y < 67.00)
                throw new CoreException("Долгота должна быть в диапазоне от 52.00 до 67.00");

        }
    }

    @Override
    public String toString() {
        return "RouteDirection{direction='%s', path=%s}".formatted(direction, path);
    }
}