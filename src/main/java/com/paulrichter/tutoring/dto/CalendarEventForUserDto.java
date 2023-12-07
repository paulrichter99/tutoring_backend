package com.paulrichter.tutoring.dto;

import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class CalendarEventForUserDto implements Serializable {
    private final Long id;
    private final Integer eventDuration;
    private final CalendarDateDto eventDate;

    public CalendarEventForUserDto(Long id, Integer eventDuration, CalendarDateDto eventDate) {
        this.id = id;
        this.eventDuration = eventDuration;
        this.eventDate = eventDate;
    }

    public CalendarEventForUserDto(CalendarEvent calendarEvent) {
        this.id = calendarEvent.getId();
        this.eventDuration = calendarEvent.getEventDuration();

        this.eventDate = new CalendarDateDto(calendarEvent.getEventDate().getId(), calendarEvent.getEventDate().getDateTime());
    }

    public Long getId() {
        return id;
    }

    public Integer getEventDuration() {
        return eventDuration;
    }

    public CalendarDateDto getEventDate() {
        return eventDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarEventForUserDto entity = (CalendarEventForUserDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.eventDuration, entity.eventDuration) &&
                Objects.equals(this.eventDate, entity.eventDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventDuration, eventDate);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "eventDuration = " + eventDuration + ", " +
                "eventDate = " + eventDate + ")";
    }
}
