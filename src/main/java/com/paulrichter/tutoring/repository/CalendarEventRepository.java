package com.paulrichter.tutoring.repository;

import com.paulrichter.tutoring.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    Optional<CalendarEvent> findByEventName(String eventName);

    @Query(value = "SELECT ce.* " +
            "FROM user u " +
            "JOIN user_calendar_event uce ON u.id = uce.event_users_id " +
            "JOIN calendar_event ce ON uce.calendar_events_id = ce.id " +
            "JOIN calendar_date cd ON ce.event_date = cd.id " +
            "WHERE u.username = :username " +
            "AND cd.date_time >= :startDate " +
            "AND cd.date_time <= :endDate " +
            "ORDER BY cd.date_time ASC", nativeQuery = true)
    List<CalendarEvent> findEventsByUsernameAndDateRange(
            @Param("username") String username,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}