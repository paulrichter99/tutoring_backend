package com.paulrichter.tutoring.dto;

import com.paulrichter.tutoring.dto.user.UserDtoForEvent;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalendarEventDto implements Serializable {

    private final Long id;
    private final String eventName;
    private final Integer eventDuration;
    private final CalendarDateDto eventDate;
    private final List<UserDtoForEvent> eventUsers;

    public CalendarEventDto(Long id, String eventName, Integer eventDuration, CalendarDateDto eventDate, List<UserDtoForEvent> eventUsers) {
        this.id = id;
        this.eventName = eventName;
        this.eventDuration = eventDuration;
        this.eventDate = eventDate;
        this.eventUsers = eventUsers;
    }

    public CalendarEventDto(CalendarEvent calendarEvent){
        this.id = calendarEvent.getId();
        this.eventName = calendarEvent.getEventName();
        this.eventDuration = calendarEvent.getEventDuration();

        this.eventDate = new CalendarDateDto(calendarEvent.getEventDate().getId(), calendarEvent.getEventDate().getDateTime());

        List<UserDtoForEvent> eventUsers = new ArrayList<>();
        for(User user: calendarEvent.getEventUsers()){
            eventUsers.add(new UserDtoForEvent(user.getUsername()));
        }
        this.eventUsers = eventUsers;
    }

    public Long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public Integer getEventDuration() {
        return eventDuration;
    }

    public CalendarDateDto getEventDate() {
        return eventDate;
    }

    public List<UserDtoForEvent> getEventUsers() {
        return eventUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarEventDto entity = (CalendarEventDto) o;
        return  Objects.equals(this.id, entity.id) &&
                Objects.equals(this.eventName, entity.eventName) &&
                Objects.equals(this.eventDuration, entity.eventDuration) &&
                Objects.equals(this.eventDate, entity.eventDate) &&
                Objects.equals(this.eventUsers, entity.eventUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, eventDuration, eventDate, eventUsers);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "eventId = " + id + ", " +
                "eventName = " + eventName + ", " +
                "eventDuration = " + eventDuration + ", " +
                "eventDate = " + eventDate + ", " +
                "eventUsers = " + eventUsers + ")";
    }
}
