package com.paulrichter.tutoring.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class UserDto implements Serializable {
    private final String username;
    private final List<CalendarEventDto> calendarEvents;

    public UserDto(String username, List<CalendarEventDto> calendarEvents) {
        this.username = username;
        this.calendarEvents = calendarEvents;
    }

    public String getUsername() {
        return username;
    }

    public List<CalendarEventDto> getCalendarEvents() {
        return calendarEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.username, entity.username) &&
                Objects.equals(this.calendarEvents, entity.calendarEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, calendarEvents);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "username = " + username + ", " +
                "calendarEvents = " + calendarEvents + ")";
    }
}
