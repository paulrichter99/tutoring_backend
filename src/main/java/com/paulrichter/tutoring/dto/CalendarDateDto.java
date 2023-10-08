package com.paulrichter.tutoring.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarDateDto implements Serializable {
    private final LocalDateTime dateTime;

    public CalendarDateDto(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
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
                "date = " + dateTime + ")";
    }
}
