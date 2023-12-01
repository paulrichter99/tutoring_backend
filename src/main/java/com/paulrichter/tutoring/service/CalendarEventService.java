package com.paulrichter.tutoring.service;

import com.paulrichter.tutoring.dto.CalendarEventDto;
import com.paulrichter.tutoring.dto.CalendarEventForUserDto;
import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.repository.CalendarDateRepository;
import com.paulrichter.tutoring.repository.CalendarEventRepository;
import com.paulrichter.tutoring.repository.UserRepository;
import com.paulrichter.tutoring.util.CalendarEventUtilService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarEventService {
    private final CalendarEventRepository calendarEventRepository;
    private final UserRepository userRepository;
    private final CalendarDateRepository calendarDateRepository;
    private final CalendarEventUtilService calendarEventUtilService;

    @Value("${tutoring.app.mainUserName}")
    private String mainUserName;

    public CalendarEventService (CalendarEventRepository calendarEventRepository,
                                 UserRepository userRepository,
                                 CalendarDateRepository calendarDateRepository,
                                 CalendarEventUtilService calendarEventUtilService){
        this.calendarEventRepository = calendarEventRepository;
        this.userRepository = userRepository;
        this.calendarDateRepository = calendarDateRepository;
        this.calendarEventUtilService = calendarEventUtilService;
    }

    public CalendarEventDto findDtoById(long id){
        Optional<CalendarEvent> optionalCalendarEvent = calendarEventRepository.findById(id);
        if(optionalCalendarEvent.isEmpty()) return null;

        CalendarEvent calendarEvent = optionalCalendarEvent.get();

        return new CalendarEventDto(calendarEvent);
    }

    public CalendarEventDto update(CalendarEvent calendarEvent){
        if(!calendarEventUtilService.validateDuration(calendarEvent)) return null;
        Optional<CalendarEvent> optionalCalendarEvent = calendarEventRepository.findById(calendarEvent.getId());
        if(optionalCalendarEvent.isEmpty()) return null;

        CalendarEvent persistedEvent = optionalCalendarEvent.get();

        // check date compatibility of the event
        if(!calendarEventUtilService.checkDateCompatibility(calendarEvent)) return null;

        persistedEvent.setEventName(calendarEvent.getEventName());
        persistedEvent.setEventDuration(calendarEvent.getEventDuration());

        List<User> userFromCalendarEvent = new ArrayList<>();
        // make sure we get the correct persisted user
        for(User eventUser: calendarEvent.getEventUsers()){
            // TODO: We have to adjust/remove the event from the users, if cascading is not enough
            //  -> Pls check
            this.userRepository.findByUsername(eventUser.getUsername()).ifPresent(userFromCalendarEvent::add);
        }
        persistedEvent.setEventUsers(userFromCalendarEvent);
        // same as user
        List<CalendarDate> calendarDatesFromCalendarEvent = new ArrayList<>();
        // make sure we get the correct persisted date
        for(CalendarDate eventDate: calendarEvent.getEventDates()){
            CalendarDate calendarDate = this.calendarDateRepository.findById(eventDate.getId()).orElse(null);
            if(calendarDate == null){ return null; }

            calendarDate.setDateTime(eventDate.getDateTime());
            calendarDateRepository.save(calendarDate);
            calendarDatesFromCalendarEvent.add(calendarDate);

        }
        persistedEvent.setEventDates(calendarDatesFromCalendarEvent);
        calendarEventRepository.save(persistedEvent);

        return new CalendarEventDto(persistedEvent);
    }

    public CalendarEventDto save(CalendarEvent calendarEvent, boolean isPrivate){
        if(!isPrivate && !(calendarEvent.getEventUsers().contains((userRepository.findMainTutor(mainUserName))))){
            calendarEvent.getEventUsers().add(userRepository.findMainTutor(mainUserName));
        }
        // check date compatibility of the event
        if(!calendarEventUtilService.checkDateCompatibility(calendarEvent)) return null;
        // save the new CalendarEvent
        calendarEventRepository.save(calendarEvent);

        // set the calendarEventProperties for the user and date
        for(User eventUser: calendarEvent.getEventUsers()){
            this.userRepository.findByUsername(eventUser.getUsername()).ifPresent(user -> {user.getCalendarEvents().add(calendarEvent);});
        }

        for(CalendarDate eventDate: calendarEvent.getEventDates()){
            CalendarDate calendarDate = this.calendarDateRepository.findById(eventDate.getId()).orElse(null);
            if(calendarDate == null){ return null; }

            calendarDate.setDateTime(eventDate.getDateTime());
            calendarDate.setCalendarEvent(calendarEvent);

            calendarDateRepository.save(calendarDate);
        }
        return new CalendarEventDto(calendarEvent);
    }


    public List<CalendarEventDto> findAllForAdmin(){
        List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();

        List<CalendarEventDto> calendarEventDtoList = new ArrayList<>();

        for(CalendarEvent calendarEvent: calendarEvents){
            calendarEventDtoList.add(new CalendarEventDto(calendarEvent));
        }
        return calendarEventDtoList;
    }

    public List<CalendarEventForUserDto> findAllFromMainTutor(){
        User mainUser  = userRepository.findMainTutor(mainUserName);

        List<CalendarEvent> calendarEvents = mainUser.getCalendarEvents();

        List<CalendarEventForUserDto> calendarEventForUserDto = new ArrayList<>();

        for(CalendarEvent calendarEvent: calendarEvents){
            calendarEventForUserDto.add(new CalendarEventForUserDto(calendarEvent));
        }
        return calendarEventForUserDto;
    }
}
