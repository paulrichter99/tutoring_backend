package com.paulrichter.tutoring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.*;

@Entity
@Table(name = "calendar_event")
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name", nullable = false)
    @Size(min = 5, max = 40, message = "eventName should be at least 5 and maximum of 40 characters")
    private String eventName;

    @Column(name = "event_duration", nullable = false)
    @Min(value = 60, message = "eventDuration should be between 60, 90 or 120")
    @Max(value = 120, message = "eventDuration should be between 60, 90 or 120")
    private Integer eventDuration;

    @ManyToMany(mappedBy = "calendarEvents", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<User> eventUsers = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eventDate")
    private CalendarDate eventDate;

    public CalendarEvent() {
    }

    public CalendarEvent(String eventName, Integer eventDuration, CalendarDate eventDate) {
        this.eventName = eventName;
        this.eventDuration = eventDuration;
        this.eventDate = eventDate;
    }

    public CalendarDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(CalendarDate eventDates) {
        this.eventDate = eventDates;
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