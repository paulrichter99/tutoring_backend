package com.paulrichter.tutoring.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_date")
public class CalendarDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    private CalendarEvent calendarEvent;

    public CalendarDate() {
    }

    public CalendarDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }

    public void setCalendarEvent(CalendarEvent calendarEvent) {
        this.calendarEvent = calendarEvent;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}