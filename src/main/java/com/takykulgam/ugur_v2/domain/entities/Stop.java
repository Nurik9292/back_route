package com.takykulgam.ugur_v2.domain.entities;

import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.values.Point;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Stop implements Domain{

    private long id;
    private final String title;
    private final Point location;
    private final long cityId;

    public Stop(String title, Point location, long cityId) {
        this.title = title;
        this.location = location;
        this.cityId = cityId;
    }

    public Stop(long id, String title, Point location, long cityId) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.cityId = cityId;
    }

    public void validateTitle() {
        if (title == null || title.isEmpty())
            throw new CoreException("Название остановки не может быть пустым.");
        if (title.length() < 3)
            throw new CoreException("Название остановки должно содержать не менее 3 символов.");

    }

    public void validateLocation() {
        if (location == null)
            throw new CoreException("Координаты остановки не заданы.");
        location.validation();
    }

    public void validateCity() {
        if (cityId <= 0)
            throw new CoreException("Остановка должна быть привязана к городу.");
    }


    @Override
    public String toString() {
        return "Stop{id=%d, title='%s', location=%s, cityId=%s}".formatted(id, title, location, cityId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return id == stop.id && Objects.equals(title, stop.title) && Objects.equals(location, stop.location) && Objects.equals(cityId, stop.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, location, cityId);
    }
}
