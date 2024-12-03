package com.takykulgam.ugur_v2.core.domain.entities.staff;

import com.takykulgam.ugur_v2.core.domain.exceptions.staff.StaffNameException;
import com.takykulgam.ugur_v2.core.domain.exceptions.staff.StaffPasswordException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Staff {

    private final String name;
    private boolean isAdmin;
    private final Password password;


    public Staff(String name) {
        this(name, null, false);
    }

    public Staff(String name, boolean isAdmin) {
        this(name, null, isAdmin);
    }

    public Staff(String name, String rawPassword, boolean isAdmin) {
        validateName(name);
        this.name = name;
        this.isAdmin = isAdmin;
        this.password = new Password(rawPassword);
        this.password.validate();
    }

    public boolean isAdmin() {
        return isAdmin;
    }


    private void validateName(String name) {
        if (name == null || name.length() < 3) {
            throw new StaffNameException("Name must be at least 3 characters long");
        }
    }


    public record Password(String value) {

        public void validate() {
            if (value == null || value.length() < 6) {
                throw new StaffPasswordException("Password must be at least 6 characters long");
            }
        }

            @Override
            public String toString() {
                return "******";
            }

        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return isAdmin == staff.isAdmin && Objects.equals(name, staff.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isAdmin);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
