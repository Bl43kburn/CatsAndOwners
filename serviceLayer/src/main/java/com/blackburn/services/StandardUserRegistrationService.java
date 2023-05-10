package com.blackburn.services;

import com.blackburn.dao.UserRepository;
import com.blackburn.exceptions.UserException;
import com.blackburn.model.Authority;
import com.blackburn.security.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:service.properties")
public class StandardUserRegistrationService implements UserRegistrationService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final Environment env;

    @PostConstruct
    public void init() {
        if (userRepository.findByLogin("root") == null)
            userRepository.save(new Authority(env.getProperty("root.username"), env.getProperty("root.password"), Role.ROOT));
    }

    @Autowired
    public StandardUserRegistrationService(UserRepository repository, PasswordEncoder encoder, Environment env) {
        this.userRepository = repository;
        this.encoder = encoder;
        this.env = env;
    }

    @Override
    public String addUser(String login, String password) {
        if (login.isBlank() || password.isBlank())
            throw UserException.EmptyCredentials();

        Authority authority = userRepository.findByLogin(login);

        if (authority != null)
            throw UserException.AlreadyExists();

        String encodedPassword = encoder.encode(password);
        userRepository.save(new Authority(login, encodedPassword, Role.OWNER));
        return login;
    }
}
