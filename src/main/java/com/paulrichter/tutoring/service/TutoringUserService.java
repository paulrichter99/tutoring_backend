package com.paulrichter.tutoring.service;

import com.paulrichter.tutoring.Enum.ERole;
import com.paulrichter.tutoring.dto.user.UserDto;
import com.paulrichter.tutoring.dto.user.UserSettingsDto;
import com.paulrichter.tutoring.model.Role;
import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TutoringUserService {
    private final UserRepository userRepository;

    public TutoringUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public List<String> findAllUsernames(){
        List<String> usernames = new ArrayList<>();

        for(User u: userRepository.findAll()){
            usernames.add(u.getUsername());
        }
        return usernames;
    }

    public ResponseEntity<UserDto> saveUserInformation(User loggedInUser, UserSettingsDto userSettingsDto){
        User changeUser = userRepository.findByUsername(userSettingsDto.getUsername()).orElse(null);
        if((!loggedInUser.getRoles().contains(new Role(ERole.ROLE_ADMIN))
            && !loggedInUser.getUsername().equals(userSettingsDto.getUsername()))
            || changeUser == null)
        {
            return ResponseEntity.badRequest().build();
        }

        if(new UserSettingsDto(changeUser).equals(userSettingsDto)){
            return ResponseEntity.ok(new UserDto(changeUser));
        }

        changeUser.setFirstName(userSettingsDto.getFirstName());
        changeUser.setLastName(userSettingsDto.getLastName());

        changeUser.setPhoneNumber(userSettingsDto.getPhoneNumber());
        changeUser.setAddress(userSettingsDto.getAddress());

        changeUser.setBirthday(userSettingsDto.getBirthday());
        changeUser.setEmail(userSettingsDto.getEmail());

        changeUser.setSchool(userSettingsDto.getSchool());
        changeUser.setGrade(userSettingsDto.getGrade());

        // System.out.println(changeUser.toString());
        this.userRepository.save(changeUser);

        return ResponseEntity.ok(new UserDto(changeUser));
    }
}
