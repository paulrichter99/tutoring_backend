package com.paulrichter.tutoring.dto.user;

import com.paulrichter.tutoring.dto.CalendarEventDto;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.model.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto implements Serializable {
    private final String username;
    private final List<CalendarEventDto> calendarEvents;

    private String firstName;
    private String lastName;
    private String address;
    private LocalDate birthday;

    private String email;
    private String phoneNumber;

    private Integer grade;
    private String school;

    public UserDto(String username, List<CalendarEvent> calendarEvents) {
        this.username = username;

        List<CalendarEventDto> calendarEventDtoList = new ArrayList<>();
        for(CalendarEvent event: calendarEvents){
            calendarEventDtoList.add(new CalendarEventDto(event));
        }
        this.calendarEvents = calendarEventDtoList;
    }

    public UserDto(User user) {
        this.username = user.getUsername();

        List<CalendarEventDto> calendarEventDtoList = new ArrayList<>();
        for(CalendarEvent event: user.getCalendarEvents()){
            calendarEventDtoList.add(new CalendarEventDto(event));
        }
        this.calendarEvents = calendarEventDtoList;

        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();

        this.address = user.getAddress();
        this.birthday = user.getBirthday();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();

        this.grade = user.getGrade();
        this.school = user.getSchool();
    }

    public String getUsername() {
        return username;
    }

    public List<CalendarEventDto> getCalendarEvents() {
        return calendarEvents;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getSchool() {
        return school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) &&
                Objects.equals(calendarEvents, userDto.calendarEvents) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(address, userDto.address) &&
                Objects.equals(birthday, userDto.birthday) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(phoneNumber, userDto.phoneNumber) &&
                Objects.equals(grade, userDto.grade) &&
                Objects.equals(school, userDto.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, calendarEvents, firstName, lastName, address, birthday, email, phoneNumber, grade, school);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", calendarEvents=" + calendarEvents +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", grade=" + grade +
                ", school='" + school + '\'' +
                '}';
    }
}
