package com.paulrichter.tutoring.controller.rest;
import com.paulrichter.tutoring.dto.CalendarEventDto;
import com.paulrichter.tutoring.dto.CalendarEventForUserDto;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.service.CalendarEventService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<?> updateCalendarEventById(@Valid @RequestBody CalendarEvent calendarEvent){
        CalendarEventDto calendarEventDto = calendarEventService.update(calendarEvent);
        if(calendarEventDto != null) return ResponseEntity.ok(calendarEventDto);
        else return ResponseEntity.badRequest().body("DATA_INVALID or DATE_ALREADY_OCCUPIED");
    }

    @PostMapping("/calendarEvent")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateCalendarEvent(@Valid @RequestBody CalendarEvent calendarEvent,
                                                 @RequestParam(required = false) boolean isPrivate){
        CalendarEventDto calendarEventDto = calendarEventService.save(calendarEvent, isPrivate);
        if(calendarEventDto != null) return ResponseEntity.ok(calendarEventDto);
        else return ResponseEntity.badRequest().body("DATA_INVALID or DATE_ALREADY_OCCUPIED");
    }

    @GetMapping("/calendarEvent/all/admin")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CalendarEventDto>> getAllCalendarEvents(){
        return ResponseEntity.ok(calendarEventService.findAllForAdmin());
    }

    @GetMapping("/calendarEvent/all/user")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CalendarEventForUserDto>> getAllCalendarEventsForUser(){
        // return List of CalendarEventForUserDto
        // TODO: we should change this to return the Events from the specific tutor or
        //  multiple tutors - for now this solution is probably fine, since the declared
        //  username is the only tutor
        return ResponseEntity.ok(calendarEventService.findAllFromMainTutor());
    }
}
