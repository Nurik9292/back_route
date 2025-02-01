package com.takykulgam.ugur_v2.domain.entities;

import com.takykulgam.ugur_v2.domain.exceptions.CoreException;

import java.util.List;

public record Route(
        String name,
        int number,
        int interval,
        RouteDirection toRouteDirection,
        RouteDirection fromRouteDirection,
        List<Long> toStops,
        List<Long> fromStops,
        long cityId
        ) implements Domain {

    public void validationName() {
        if (name == null || name.isBlank()) throw new CoreException("Имя маршрута нулевое или пустое");
        if (name.length() < 3) throw new CoreException("Название маршрута слишком короткое");
        if (name.length() > 150) throw new CoreException("Название маршрута слишком длинное");
    }

    public void validationNumber() {
        if (number <= 0) throw new CoreException("Номер Маршрута должен быть больше 0");
    }

    public void validationInterval() {
        if (interval <= 0) throw  new CoreException("Интервал должен быть больше 0");
    }

    public void validationStops() {
        if(toStops.isEmpty()) throw new CoreException("TO Остановки для маршрута не должны быть пустыми");
        if(fromStops.isEmpty()) throw new CoreException("FROM Остановки для маршрута не должны быть пустыми");
    }

    public void validateCity() {
        if (cityId <= 0)
            throw new CoreException("Остановка должна быть привязана к городу.");
    }

    public void validationDirections() {
        toRouteDirection.validationDirection();
        fromRouteDirection.validationDirection();
        toRouteDirection.validationPath();
        fromRouteDirection.validationPath();
    }
}
