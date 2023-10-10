package com.paulrichter.tutoring.controller.rest;
import com.paulrichter.tutoring.dto.CalendarEventDto;
import com.paulrichter.tutoring.service.CalendarEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CalendarEventController {
    private final CalendarEventService calendarEventService;

    public CalendarEventController (CalendarEventService calendarEventService) {
        this.calendarEventService = calendarEventService;
    }

    @GetMapping("/calendarEvent/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CalendarEventDto> getCalendarEventById(@PathVariable long id) throws Exception {
        return ResponseEntity.ok(calendarEventService.findById(id));
    }

    @GetMapping("/calendarEvent/all")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CalendarEventDto>> getAllCalendarEvents() throws Exception {
        return ResponseEntity.ok(calendarEventService.findAll());
    }
}
