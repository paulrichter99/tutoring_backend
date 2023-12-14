package com.paulrichter.tutoring.dto.user;

import com.paulrichter.tutoring.model.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class UserSettingsDto implements Serializable {

    private final String username;

    private final String firstName;
    private final String lastName;
    private final String address;
    private final LocalDate birthday;

    private final String email;
    private final String phoneNumber;

    private final Integer grade;
    private final String school;


    public UserSettingsDto(String username, String firstName, String lastName,
                           LocalDate birthday, String address, String email, String phoneNumber,
                           Integer grade, String school) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.school = school;
    }

    public UserSettingsDto(User user){
        this.username = user.getUsername();

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
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
        UserSettingsDto entity = (UserSettingsDto) o;
        return
                Objects.equals(this.username, entity.username) &&
                Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.birthday, entity.birthday) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.phoneNumber, entity.phoneNumber) &&
                Objects.equals(this.grade, entity.grade) &&
                Objects.equals(this.school, entity.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, lastName, address, birthday, email, phoneNumber, grade, school);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "username = " + username + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "address = " + address + ", " +
                "birthday = " + birthday + ", " +
                "email = " + email + ", " +
                "phoneNumber = " + phoneNumber + ", " +
                "grade = " + grade + ", " +
                "school = " + school + ")";
    }
}
