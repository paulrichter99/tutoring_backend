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
import org.springframework.transaction.annotation.Transactional;

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
            this.userRepository.findByUsername(eventUser.getUsername()).ifPresent(userFromCalendarEvent::add);
        }
        persistedEvent.setEventUsers(userFromCalendarEvent);

        // make sure we get the correct persisted date
        CalendarDate calendarDate = this.calendarDateRepository.findById(calendarEvent.getEventDate().getId()).orElse(null);
        if(calendarDate == null){ return null; }

        calendarDate.setDateTime(calendarEvent.getEventDate().getDateTime());
        calendarDateRepository.save(calendarDate);

        persistedEvent.setEventDate(calendarDate);
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

        // before saving we ensure to get the users by username from the database before we add the event
        // else the saving will fail since the user is not valid
        List<User> usersForEvent = new ArrayList<>();
        for(User eventUser: calendarEvent.getEventUsers()){
            // additionally set the calendarEventProperties for the user and date
            User user = this.userRepository.findByUsername(eventUser.getUsername()).orElse(null);
            if(user == null) {
                return null;
            }
            usersForEvent.add(user);
            user.getCalendarEvents().add(calendarEvent);
        }
        // set the real users for the event
        calendarEvent.setEventUsers(usersForEvent);
        System.out.println(calendarEvent.toString());

        calendarEventRepository.save(calendarEvent);


        CalendarDate calendarDate = this.calendarDateRepository.findById(calendarEvent.getEventDate().getId()).orElse(null);
        if(calendarDate == null){ return null; }

        calendarDate.setDateTime(calendarEvent.getEventDate().getDateTime());
        calendarDate.setCalendarEvent(calendarEvent);

        calendarDateRepository.save(calendarDate);

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

    public List<CalendarEventDto> findEventsByUsernameAndDateRange(String username, String startDate, String endDate){
        // we need to add this, so we actually find all dates that are on the same day
        startDate += "T00:00Z";
        endDate += "T23:59Z";
        List<CalendarEvent> calendarEvents = calendarEventRepository.findEventsByUsernameAndDateRange(username, startDate, endDate);

        List<CalendarEventDto> calendarEventDtos = new ArrayList<>();
        for(CalendarEvent calendarEvent: calendarEvents){
            calendarEventDtos.add(new CalendarEventDto(calendarEvent));
        }
        return calendarEventDtos;
    }
}
