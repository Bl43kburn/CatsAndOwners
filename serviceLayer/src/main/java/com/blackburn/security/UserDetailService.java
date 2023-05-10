package com.blackburn.security;

import com.blackburn.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("UserDetailService")
public class UserDetailService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserDetailService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return DefaultUserDetails.fromUser(repository.findByLogin(username));
    }
}
