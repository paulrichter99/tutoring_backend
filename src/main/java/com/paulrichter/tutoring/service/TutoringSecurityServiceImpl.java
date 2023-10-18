package com.paulrichter.tutoring.service;

import com.paulrichter.tutoring.repository.RoleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TutoringSecurityServiceImpl implements SecurityService{

    @Override
    public String findLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof TutoringUserDetailsImpl) {
            return ((TutoringUserDetailsImpl)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    public void autoLogin(String username, String password) {
        // not yet implemented
    }
}
