package com.takykulgam.ugur_v2.domain.entities.staff;

public class Staff {

    private String name;
    private Role role;


    public Staff() {

    }

    public Staff(String name) {
        this.name = name;
        this.role = Role.EMPLOYEE;
    }

    public Staff(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
