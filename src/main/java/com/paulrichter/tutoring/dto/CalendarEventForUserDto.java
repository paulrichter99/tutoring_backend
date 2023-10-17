package com.paulrichter.tutoring.dto;

import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalendarEventForUserDto implements Serializable {
    private final Long id;
    private final Integer eventDuration;
    private final List<CalendarDateDto> eventDates;

    public CalendarEventForUserDto(Long id, Integer eventDuration, List<CalendarDateDto> eventDates) {
        this.id = id;
        this.eventDuration = eventDuration;
        this.eventDates = eventDates;
    }

    public CalendarEventForUserDto(CalendarEvent calendarEvent) {
        this.id = calendarEvent.getId();
        this.eventDuration = calendarEvent.getEventDuration();

        List<CalendarDateDto> calendarDates = new ArrayList<>();
        for(CalendarDate date: calendarEvent.getEventDates()){
            calendarDates.add(new CalendarDateDto(date.getId(), date.getDateTime()));
        }
        this.eventDates = calendarDates;
    }

    public Long getId() {
        return id;
    }

    public Integer getEventDuration() {
        return eventDuration;
    }

    public List<CalendarDateDto> getEventDates() {
        return eventDates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarEventForUserDto entity = (CalendarEventForUserDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.eventDuration, entity.eventDuration) &&
                Objects.equals(this.eventDates, entity.eventDates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventDuration, eventDates);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "eventDuration = " + eventDuration + ", " +
                "eventDates = " + eventDates + ")";
    }
}
