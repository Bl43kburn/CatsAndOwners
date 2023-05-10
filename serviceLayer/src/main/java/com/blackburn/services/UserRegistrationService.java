package com.blackburn.services;

import org.springframework.stereotype.Service;

@Service
public interface UserRegistrationService {
    public String addUser(String login, String password);
}
