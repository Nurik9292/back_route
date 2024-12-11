package com.takykulgam.ugur_v2.core.domain.entities;

import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class City {

    private long id;
    private String title;

    public City() {}

    public City(String title) {
        this.title = title;
    }

    public City(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void validateTitle() {
        if (title == null || title.isEmpty())
            throw new CoreException("Название города не может быть пустым.");
        if (title.length() < 3)
            throw new CoreException("Название города должно содержать не менее 3 символов.");
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id && Objects.equals(title, city.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
