package com.paulrichter.tutoring.service;

import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TutoringUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public TutoringUserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return TutoringUserDetailsImpl.build(user);
    }
}
