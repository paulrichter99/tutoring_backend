package com.paulrichter.tutoring.repository;

import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarDateRepository extends JpaRepository<CalendarDate, Long> { }