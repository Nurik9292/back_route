package com.takykulgam.ugur_v2.core.boundaries.dto;

public class AuthStaff {

    private String name;
    private String password;

    public AuthStaff(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthStaff{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
