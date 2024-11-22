package com.takykulgam.ugur_v2.core.domain.entities.staff;

import com.takykulgam.ugur_v2.core.domain.exceptions.staff.StaffNameException;
import com.takykulgam.ugur_v2.core.domain.exceptions.staff.StaffPasswordException;

public class Staff {

    private String name;
    private boolean isAdmin;
    private String password;

    public Staff() {
    }

    public Staff(String name) {
        this.name = name;
        this.isAdmin = false;
    }

    public Staff(String name, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public Staff(String name, String password, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void validatePassword() {
        if (password == null || password.length() < 6)
            throw new StaffPasswordException("Password must be at least 6 characters long");
    }

    public void validateName() {
        if (name == null || name.length() < 3)
            throw new StaffNameException("Name must be at least 3 characters long");
    }


    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
