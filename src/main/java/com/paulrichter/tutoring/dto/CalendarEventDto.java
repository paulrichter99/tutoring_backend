package com.paulrichter.tutoring.dto;

import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalendarEventDto implements Serializable {

    private final Long eventId;
    private final String eventName;
    private final Integer eventDuration;
    private final List<CalendarDateDto> eventDates;
    private final List<UserDtoForEvent> eventUsers;

    public CalendarEventDto(Long eventId, String eventName, Integer eventDuration, List<CalendarDateDto> eventDates, List<UserDtoForEvent> eventUsers) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDuration = eventDuration;
        this.eventDates = eventDates;
        this.eventUsers = eventUsers;
    }

    public CalendarEventDto(CalendarEvent calendarEvent){
        this.eventId = calendarEvent.getId();
        this.eventName = calendarEvent.getEventName();
        this.eventDuration = calendarEvent.getEventDuration();

        List<CalendarDateDto> calendarDates = new ArrayList<>();
        for(CalendarDate date: calendarEvent.getCalendarDates()){
            calendarDates.add(new CalendarDateDto(date.getDateTime()));
        }
        this.eventDates = calendarDates;

        List<UserDtoForEvent> eventUsers = new ArrayList<>();
        for(User user: calendarEvent.getEventUsers()){
            eventUsers.add(new UserDtoForEvent(user.getUsername()));
        }
        this.eventUsers = eventUsers;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public Integer getEventDuration() {
        return eventDuration;
    }

    public List<CalendarDateDto> getEventDates() {
        return eventDates;
    }

    public List<UserDtoForEvent> getEventUsers() {
        return eventUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarEventDto entity = (CalendarEventDto) o;
        return  Objects.equals(this.eventId, entity.eventId) &&
                Objects.equals(this.eventName, entity.eventName) &&
                Objects.equals(this.eventDuration, entity.eventDuration) &&
                Objects.equals(this.eventDates, entity.eventDates) &&
                Objects.equals(this.eventUsers, entity.eventUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, eventDuration, eventDates, eventUsers);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "eventId = " + eventId + ", " +
                "eventName = " + eventName + ", " +
                "eventDuration = " + eventDuration + ", " +
                "eventDate = " + eventDates + ", " +
                "eventUsers = " + eventUsers + ")";
    }
}
