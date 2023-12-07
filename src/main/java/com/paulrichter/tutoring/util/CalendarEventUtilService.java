package com.paulrichter.tutoring.util;

import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.repository.CalendarDateRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CalendarEventUtilService {

    private final CalendarDateRepository calendarDateRepository;

    public CalendarEventUtilService(CalendarDateRepository calendarDateRepository) {
        this.calendarDateRepository = calendarDateRepository;
    }

    public boolean checkDateCompatibility(CalendarEvent calendarEvent){
        // we have to calculate (for every date of the event) startDate, betweenDates and endDate
        int eventDurationSteps = calendarEvent.getEventDuration() / 30;

        CalendarDate calendarDate = calendarEvent.getEventDate();
        ZonedDateTime checkDate;
        // going from -90 minutes to plus eventDuration
        //  (-120 would always work since 08:00-10:00 and 10:00-xx:xx is not interfering)
        for(int i = -3; i < eventDurationSteps; i++) {
            checkDate = calendarDate.getDateTime().plusSeconds((long) i * 60 * 30);
            List<CalendarDate> calendarDatesToCheck = calendarDateRepository.findByDateTime(checkDate).orElse(null);
            if(calendarDatesToCheck == null) continue;

            for(CalendarDate calendarDateToCheck: calendarDatesToCheck) {
                boolean checkValidity = false;
                for (User user : calendarDateToCheck.getCalendarEvent().getEventUsers()) {
                    for (User checkUser : calendarEvent.getEventUsers()) {
                        if (user.getUsername().equals(checkUser.getUsername())) {
                            checkValidity = true;
                            break;
                        }
                    }
                    if (checkValidity) {
                        break;
                    }
                }

                // we are fine if the new and old event have the same id
                if ((!(Objects.equals(calendarDateToCheck.getCalendarEvent().getId(), calendarEvent.getId()))) && checkValidity) {
                    //date already exists
                    if (i < 0) {
                        // checking backwards
                        //  we are negative, if we find a date, we have to check whether the found event's duration
                        //  is bigger or equal to our current duration (if -120min our event has to have a duration of 120min)
                        //  e.g  new event   -> 10:00 + 90min
                        //       check 1     -> 08:30 -> date found!
                        //          check 2     -> foundDate.event.duration > 90? -> return null else continue
                        if (calendarDateToCheck.getCalendarEvent().getEventDuration() > Math.abs(i * 30)) {
                            return false;
                        }
                    } else {
                        // checking forward
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean validateDuration(CalendarEvent calendarEventDto){
        if(calendarEventDto.getEventDuration() % 30 != 0){
            return false;
        }
        return true;
    }
}