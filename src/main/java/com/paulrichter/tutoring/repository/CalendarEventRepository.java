package com.paulrichter.tutoring.repository;

import com.paulrichter.tutoring.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    Optional<CalendarEvent> findByEventName(String eventName);
}