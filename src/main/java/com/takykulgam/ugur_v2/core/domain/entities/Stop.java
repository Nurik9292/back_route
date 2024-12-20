package com.takykulgam.ugur_v2.core.domain.entities;

import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Stop {

    private long id;
    private final String title;
    private final Point location;
    private final City city;

    public Stop(String title, Point location, City city) {
        this.title = title;
        this.location = location;
        this.city = city;
    }

    public Stop(long id, String title, Point location, City city) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.city = city;
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
        if (city == null)
            throw new CoreException("Остановка должна быть привязана к городу.");
    }

    public record Point(double x, double y) {

        public void validation() {
            if (x > 35.00 && x < 42.00)
                throw new CoreException("Широта должна быть в диапазоне от 35.00 до 42.00");

            if (y > 52.00 && y < 67.00)
                throw new CoreException("Долгота должна быть в диапазоне от 52.00 до 67.00");
        }

        @Override
        public String toString() {
            return "(%s, %s)".formatted(x, y);
        }
    }

    public record City(long id) {
        @Override
        public String toString() {
            return "(%d)".formatted(id);
        }
    }

    @Override
    public String toString() {
        return "Stop{id=%d, title='%s', location=%s, city=%s}".formatted(id, title, location, city);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return id == stop.id && Objects.equals(title, stop.title) && Objects.equals(location, stop.location) && Objects.equals(city, stop.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, location, city);
    }
}
