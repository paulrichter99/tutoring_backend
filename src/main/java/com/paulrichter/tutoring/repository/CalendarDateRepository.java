package com.paulrichter.tutoring.repository;

import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface CalendarDateRepository extends JpaRepository<CalendarDate, Long> {
    Optional<CalendarDate> findById(Long id);

    Optional<CalendarDate> findByDateTime(ZonedDateTime dateTime);
}