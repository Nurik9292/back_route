package com.takykulgam.ugur_v2.core.domain.entities;

import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Stop {

    private long id;
    private String title;
    private Point location;
    private City city;

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

        double lat = location.x;
        double lng = location.y;
        if (lat < -37 || lat > 39) {
            throw new IllegalArgumentException("Широта должна быть в диапазоне от -37 до 39.");
        }
        if (lng < -57 || lng > 58) {
            throw new IllegalArgumentException("Долгота должна быть в диапазоне от -57 до 58.");
        }
    }

    public void validateCity() {
        if (city == null)
            throw new CoreException("Остановка должна быть привязана к городу.");
    }

    public record Point(double x, double y) {
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public record City(long id) {
        @Override
        public String toString() {
            return "(" + id + ")";
        }
    }

    @Override
    public String toString() {
        return "Stop{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", location=" + location +
                ", city=" + city +
                '}';
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
