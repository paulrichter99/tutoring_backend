package com.paulrichter.tutoring.controller.rest;
import com.paulrichter.tutoring.dto.CalendarEventDto;
import com.paulrichter.tutoring.dto.CalendarEventForUserDto;
import com.paulrichter.tutoring.model.CalendarEvent;
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
    public ResponseEntity<CalendarEventDto> getCalendarEventById(@PathVariable long id){
        return ResponseEntity.ok(calendarEventService.findDtoById(id));
    }

    @PutMapping("/calendarEvent/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CalendarEventDto> updateCalendarEventById(@RequestBody CalendarEvent calendarEvent){
        return ResponseEntity.ok(calendarEventService.update(calendarEvent));
    }

    @PostMapping("/calendarEvent")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CalendarEventDto> updateCalendarEvent(@RequestBody CalendarEvent calendarEvent){
        return ResponseEntity.ok(calendarEventService.save(calendarEvent));
    }

    @GetMapping("/calendarEvent/all/admin")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CalendarEventDto>> getAllCalendarEvents(){
        return ResponseEntity.ok(calendarEventService.findAllForAdmin());
    }

    @GetMapping("/calendarEvent/all/user")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CalendarEventForUserDto>> getAllCalendarEventsForUser(){
        // return Lust of CalendarEventForUserDto
        return ResponseEntity.ok(calendarEventService.findAllForUser());
    }
}
