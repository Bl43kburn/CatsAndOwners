package com.blackburn.security;

import com.blackburn.exceptions.UserException;
import com.blackburn.model.Authority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
public class DefaultUserDetails implements UserDetails {
    private final String login;

    private final String password;

    private final Set<SimpleGrantedAuthority> authorityList;

    private final boolean isActive = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(Authority authority) {
        if (authority == null)
            throw UserException.NotFound();

        return new DefaultUserDetails(
                authority.getLogin(), authority.getPassword(), authority.getRole().getAuthorities()
        );
    }
}
