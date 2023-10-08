package com.paulrichter.tutoring.service;

import com.paulrichter.tutoring.dto.CalendarEventDto;
import com.paulrichter.tutoring.dto.UserDtoForEvent;
import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.repository.CalendarEventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarEventService {
    private final CalendarEventRepository calendarEventRepository;

    public CalendarEventService (CalendarEventRepository calendarEventRepository){
        this.calendarEventRepository = calendarEventRepository;
    }

    public CalendarEventDto findById(long id){
        Optional<CalendarEvent> optionalCalendarEvent = calendarEventRepository.findById(id);
        if(optionalCalendarEvent.isEmpty()) return null;

        CalendarEvent calendarEvent = optionalCalendarEvent.get();

        return new CalendarEventDto(calendarEvent);
    }

    public List<CalendarEventDto> findAll(){
        List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();

        List<CalendarEventDto> calendarEventDtoList = new ArrayList<>();

        for(CalendarEvent calendarEvent: calendarEvents){
            calendarEventDtoList.add(new CalendarEventDto(calendarEvent));
        }
        return  calendarEventDtoList;
    }
}
