package com.takykulgam.ugur_v2.core.boundaries.dto;

import java.util.Objects;

public class InputStaff {

    private long id;
    private String name;
    private boolean isAdmin;
    private String password;

    public InputStaff() {}

    public InputStaff(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public InputStaff(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "InputStaff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputStaff that = (InputStaff) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }
}
