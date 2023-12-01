package com.paulrichter.tutoring.service;

import com.paulrichter.tutoring.Enum.ERole;
import com.paulrichter.tutoring.model.Role;
import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.repository.RoleRepository;
import com.paulrichter.tutoring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
}
