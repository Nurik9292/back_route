package com.takykulgam.ugur_v2.core.domain.entities;

import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;

import java.util.List;

public record Route(String name, RouteDirection routeDirection) {

    public void validationName() {
        if (name == null || name.isBlank()) throw new CoreException("Имя маршрута нулевое или пустое");
        if (name.length() < 3) throw new CoreException("Название маршрута слишком короткое");
        if (name.length() > 150) throw new CoreException("Название маршрута слишком длинное");
    }


    public static class RouteDirection {
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

    @Override
    public String toString() {
        return "Route{name='%s', routeDirection=%s}".formatted(name, routeDirection);
    }
}
