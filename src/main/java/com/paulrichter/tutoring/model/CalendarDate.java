package com.paulrichter.tutoring.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "calendar_date")
public class CalendarDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @ManyToOne
    private CalendarEvent calendarEvent;

    public CalendarDate() {
    }

    public CalendarDate(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }

    public void setCalendarEvent(CalendarEvent calendarEvent) {
        this.calendarEvent = calendarEvent;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}