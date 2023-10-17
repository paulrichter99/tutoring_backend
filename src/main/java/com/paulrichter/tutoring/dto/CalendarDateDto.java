package com.paulrichter.tutoring.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class CalendarDateDto implements Serializable {

    private final Long id;
    private final ZonedDateTime dateTime;

    public CalendarDateDto(Long id, ZonedDateTime dateTime) {
        this.id = id;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarDateDto entity = (CalendarDateDto) o;
        return Objects.equals(this.dateTime, entity.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "date = " + dateTime + ")";
    }
}
