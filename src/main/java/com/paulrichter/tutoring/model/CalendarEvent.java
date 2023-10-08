package com.paulrichter.tutoring.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "calendar_event")
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "event_duration", nullable = false)
    private Integer eventDuration;

    @ManyToMany(mappedBy = "calendarEvents", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> eventUsers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_calendar_date")
    private List<CalendarDate> calendarDates = new ArrayList<>();

    public CalendarEvent() {
    }

    public CalendarEvent(String eventName, Integer eventDuration, List<CalendarDate> calendarDates) {
        this.eventName = eventName;
        this.eventDuration = eventDuration;
        this.calendarDates = calendarDates;
    }

    public List<CalendarDate> getCalendarDates() {
        return calendarDates;
    }

    public void setCalendarDates(List<CalendarDate> calendarDates) {
        this.calendarDates = calendarDates;
    }

    public Integer getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Integer eventDuration) {
        this.eventDuration = eventDuration;
    }

    public List<User> getEventUsers() {
        return eventUsers;
    }

    public void setEventUsers(List<User> eventUsers) {
        this.eventUsers = eventUsers;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}