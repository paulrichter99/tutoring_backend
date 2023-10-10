package com.paulrichter.tutoring.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
