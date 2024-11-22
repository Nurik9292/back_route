package com.takykulgam.ugur_v2.core.boundaries.dto;

public class OutputStaff {

    private long id;
    private String name;
    private boolean role;
    private String password;

    public OutputStaff(long id, String name, String password, boolean role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
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

    public void setRole(boolean role) {
        this.role = role;
    }

    public boolean isRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "OutputStaff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
