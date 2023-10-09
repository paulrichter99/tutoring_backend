package com.paulrichter.tutoring.dto.user;

import java.io.Serializable;
import java.util.Objects;

public class UserDtoForEvent implements Serializable {
    private final String username;

    public UserDtoForEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoForEvent entity = (UserDtoForEvent) o;
        return Objects.equals(this.username, entity.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "username = " + username + ")";
    }
}
