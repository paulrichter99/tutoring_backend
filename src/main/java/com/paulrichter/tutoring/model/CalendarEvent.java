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

    @ManyToMany(mappedBy = "calendarEvents", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<User> eventUsers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_calendar_date")
    private List<CalendarDate> eventDates = new ArrayList<>();

    public CalendarEvent() {
    }

    public CalendarEvent(String eventName, Integer eventDuration, List<CalendarDate> eventDates) {
        this.eventName = eventName;
        this.eventDuration = eventDuration;
        this.eventDates = eventDates;
    }

    public List<CalendarDate> getEventDates() {
        return eventDates;
    }

    public void setEventDates(List<CalendarDate> eventDates) {
        this.eventDates = eventDates;
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